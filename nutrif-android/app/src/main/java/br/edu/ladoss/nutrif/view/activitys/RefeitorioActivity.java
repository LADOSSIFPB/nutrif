package br.edu.ladoss.nutrif.view.activitys;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.util.List;

import br.edu.ladoss.nutrif.R;
import br.edu.ladoss.nutrif.controller.DiaRefeicaoController;
import br.edu.ladoss.nutrif.controller.PessoaController;
import br.edu.ladoss.nutrif.controller.Replyable;
import br.edu.ladoss.nutrif.database.dao.AlunoDAO;
import br.edu.ladoss.nutrif.entitys.Aluno;
import br.edu.ladoss.nutrif.entitys.DiaRefeicao;
import br.edu.ladoss.nutrif.entitys.output.Erro;
import br.edu.ladoss.nutrif.util.AndroidUtil;
import br.edu.ladoss.nutrif.util.ImageUtils;
import br.edu.ladoss.nutrif.view.adapters.HorarioAdapter;
import br.edu.ladoss.nutrif.view.callback.RecycleButtonClicked;
import butterknife.Bind;
import butterknife.ButterKnife;

public class RefeitorioActivity extends AppCompatActivity implements RecycleButtonClicked, Replyable<List<DiaRefeicao>>, AccountHeader.OnAccountHeaderProfileImageListener {
    private static final int PERMISSIONS_REQUEST_CAMERA = 1;

    @Bind(R.id.carregando_layout)
    LinearLayout carregarLayout;

    @Bind(R.id.content)
    LinearLayout content;

    @Bind(R.id.recycle)
    RecyclerView recycle;

    @Bind(R.id.swiperefresh)
    SwipeRefreshLayout swipe;

    AccountHeader headerResult;

    Uri mCropImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refeitorio);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);

        buildSlideBar(toolbar, savedInstanceState);

        swipe.setColorSchemeColors(getResources().getColor(R.color.accent));
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe.requestDisallowInterceptTouchEvent(true);
                DiaRefeicaoController.gerarHorario(RefeitorioActivity.this,RefeitorioActivity.this);
                swipe.requestDisallowInterceptTouchEvent(false);
                swipe.setRefreshing(false);
            }
        });

        change(false);
        DiaRefeicaoController.gerarHorario(this, this);
    }

    public void buildSlideBar(Toolbar toolbar, Bundle savedInstanceState) {
        Aluno aluno = AlunoDAO.getInstance(this).findWithPhoto();

        ProfileDrawerItem profile = new ProfileDrawerItem()
                .withName(aluno.getNome())
                .withEmail(aluno.getEmail())
                .withIsExpanded(true);

        if (aluno.getPhoto() == null)
            profile.withIcon(R.drawable.ic_account);
        else
            profile.withIcon(ImageUtils.byteArrayToBitmap(aluno.getPhoto()));

        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withSelectionListEnabled(false)
                .withAlternativeProfileHeaderSwitching(false)
                .addProfiles(profile)
                .withHeaderBackground(R.drawable.background)
                .withOnAccountHeaderProfileImageListener(this)
                .build();

        new DrawerBuilder()
                .withAccountHeader(headerResult)
                .withToolbar(toolbar)
                .withTranslucentStatusBar(false)
                .withActionBarDrawerToggle(true)
                .withActionBarDrawerToggleAnimated(true)
                .withActivity(this)
                .withDrawerGravity(Gravity.START)
                .withSelectedItem(-1)
                .withSavedInstance(savedInstanceState)
                .build();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.sair) {
            sair();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    @SuppressLint("NewApi")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // handle result of pick image chooser
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(this, data);

            // For API >= 23 we need to check specifically that we have permissions to read external storage.
            if (CropImage.isReadExternalStoragePermissionsRequired(this, imageUri)) {
                // request permissions and handle the result in onRequestPermissionsResult()
                mCropImageUri = imageUri;
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            } else {
                // no permissions required or already grunted, can start crop image activity
                startCropImageActivity(imageUri);
            }
        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {

            final CropImage.ActivityResult result = CropImage.getActivityResult(data);
            final IProfile profile = headerResult.getActiveProfile();
            final Bitmap bitmap = BitmapFactory.decodeFile(result.getUri().getPath());
            PessoaController.uploadPhoto(this, new Replyable<Aluno>() {
                @Override
                public void onSuccess(Aluno aluno) {
                    AlunoDAO.getInstance(RefeitorioActivity.this).updatePhoto(aluno, bitmap);
                    profile.withIcon(result.getUri());
                    headerResult.updateProfile(profile);
                }

                @Override
                public void onFailure(Erro erro) {
                    Toast.makeText(RefeitorioActivity.this, erro.getMensagem(),Toast.LENGTH_LONG).show();
                }

                @Override
                public void failCommunication(Throwable throwable) {

                }
            }, new File(result.getUri().getPath()));
        }

    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if(requestCode == PERMISSIONS_REQUEST_CAMERA && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            CropImage.startPickImageActivity(RefeitorioActivity.this);

        else if (mCropImageUri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // required permissions granted, start crop image activity
            startCropImageActivity(mCropImageUri);
        }
            else {
            Toast.makeText(this, "Cancelado", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClickCallback(View view, int position) {
        Intent intent = new Intent(this, RefeicaoActivity.class);
        intent.putExtra("position", position);
        startActivity(intent);
    }

    @Override
    public void onSuccess(List<DiaRefeicao> diaRefeicaos) {
        montaTabela(diaRefeicaos);
        change(true);
    }

    @Override
    public void onFailure(Erro erro) {
        AndroidUtil.showSnackbar(RefeitorioActivity.this, erro.getMensagem());
        change(true);
    }

    @Override
    public void failCommunication(Throwable throwable) {
        AndroidUtil.showSnackbar(RefeitorioActivity.this, R.string.impossivelcarregar);
        montaTabela(DiaRefeicaoController.getRefeicoes());
        change(true);
    }

    @Override
    public boolean onProfileImageClick(View view, IProfile profile, boolean current) {
        Aluno aluno = AlunoDAO.getInstance(this).findWithPhoto();
        if(aluno.getPhoto() == null)
            tirarPhoto();
        return true;
    }

    @Override
    public boolean onProfileImageLongClick(View view, IProfile profile, boolean current) {
        return false;
    }

    public void change(final boolean ativo) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                carregarLayout.setVisibility(ativo ? View.GONE : View.VISIBLE);
                content.setVisibility(!ativo ? View.GONE : View.VISIBLE);
            }
        });
    }

    public void sair() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(R.string.sair);
        alertDialog.setMessage(getString(R.string.sairconfirmation));
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.sair),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        PessoaController.logoff(RefeitorioActivity.this);
                        startActivity(new Intent(RefeitorioActivity.this, EnterActivity.class));
                        finish();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.nao),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }


    public void startCropImageActivity(Uri image) {
        CropImage.activity(image)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setActivityTitle(getString(R.string.cut))
                .setAllowRotation(true)
                .setFixAspectRatio(true)
                .setAspectRatio(1, 1)
                .setCropShape(CropImageView.CropShape.OVAL)
                .start(this);
    }

    public void tirarPhoto() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(R.string.atencao);
        alertDialog.setMessage(getString(R.string.aviso));
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.entendido),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (ContextCompat.checkSelfPermission(RefeitorioActivity.this,
                                Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) {
                            if (ActivityCompat.shouldShowRequestPermissionRationale(RefeitorioActivity.this,
                                    Manifest.permission.CAMERA)) {
                            } else {
                                ActivityCompat.requestPermissions(RefeitorioActivity.this,
                                        new String[]{Manifest.permission.CAMERA},
                                        PERMISSIONS_REQUEST_CAMERA)   ;
                            }
                        }else{
                            CropImage.startPickImageActivity(RefeitorioActivity.this);
                        }
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.cancelar),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();

    }

    public void montaTabela(List<DiaRefeicao> refeicoes) {
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(this);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycle.setLayoutManager(gridLayoutManager);
        recycle.setAdapter(new HorarioAdapter(this, refeicoes, this));
        if (recycle.getAdapter().getItemCount() == 0) {
            recycle.setVisibility(View.GONE);
            TextView textView = new TextView(this);
            textView.setText(R.string.nodays);
            textView.setGravity(Gravity.CENTER);
            content.addView(textView);
        } else
            recycle.setVisibility(View.VISIBLE);

    }

}
