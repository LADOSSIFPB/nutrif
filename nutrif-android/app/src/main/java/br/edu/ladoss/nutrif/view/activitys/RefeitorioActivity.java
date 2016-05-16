package br.edu.ladoss.nutrif.view.activitys;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    @Bind(R.id.carregando_layout)
    LinearLayout carregarLayout;

    @Bind(R.id.content)
    LinearLayout content;

    @Bind(R.id.recycle)
    RecyclerView recycle;

    AccountHeader headerResult;

    static final int REQUEST_TAKE_PHOTO = 1;

    String mCurrentPhotoPath;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};

    public void verifyStoragePermissions() {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    this,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refeitorio);
        ButterKnife.bind(this);
        verifyStoragePermissions();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);

        buildSlideBar(toolbar, savedInstanceState);

        change(false);
        DiaRefeicaoController.gerarHorario(this, this);
    }

    public void buildSlideBar(Toolbar toolbar, Bundle savedInstanceState) {
        Aluno aluno = AlunoDAO.getInstance(this).find();

        ProfileDrawerItem profile = new ProfileDrawerItem()
                .withName(aluno.getNome())
                .withEmail(aluno.getEmail())
                .withIsExpanded(true);

        if(aluno.getPhoto() == null)
            profile.withIcon(GoogleMaterial.Icon.gmd_add_a_photo);
        else
            profile.withIcon(ImageUtils.byteToDrawable(aluno.getPhoto()));

        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withSelectionListEnabled(false)
                .withAlternativeProfileHeaderSwitching(false)
                .addProfiles(profile)
                .withOnAccountHeaderProfileImageListener(this)
                .build();

        new DrawerBuilder()
                .withAccountHeader(headerResult)
                .withToolbar(toolbar)
                .withActionBarDrawerToggle(true)
                .withActionBarDrawerToggleAnimated(true)
                .withTranslucentStatusBar(true)
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && requestCode == 1){
            CropImage.activity(Uri.parse(mCurrentPhotoPath))
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setActivityTitle(getString(R.string.cut))
                    .setAllowRotation(true)
                    .setFixAspectRatio(true)
                    .setAspectRatio(1,1)
                    .setCropShape(CropImageView.CropShape.OVAL)
                    .start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            final CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                final IProfile profile = headerResult.getActiveProfile();
                final Drawable drawable = Drawable.createFromPath(result.getUri().getPath());
                PessoaController.uploadPhoto(this, new Replyable<Aluno>() {
                    @Override
                    public void onSuccess(Aluno aluno) {
                        AlunoDAO.getInstance(RefeitorioActivity.this).updatePhoto(drawable);
                        profile.withIcon(result.getUri());
                        headerResult.updateProfile(profile);
                    }

                    @Override
                    public void onFailure(Erro erro) {

                    }

                    @Override
                    public void failCommunication(Throwable throwable) {

                    }
                },new File(result.getUri().getPath()));
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Log.w("das",ex.getMessage());
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }
    public void tirarPhoto(){
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(R.string.atencao);
        alertDialog.setMessage(getString(R.string.aviso));
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.entendido),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dispatchTakePictureIntent();
                        dialog.dismiss();
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
                        startActivity(new Intent(RefeitorioActivity.this, LoginActivity.class));
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

    @Override
    public boolean onProfileImageClick(View view, IProfile profile, boolean current) {
        Aluno aluno = AlunoDAO.getInstance(this).find();
       // if(aluno.getPhoto() == null)
            tirarPhoto();
        return true;
    }

    @Override
    public boolean onProfileImageLongClick(View view, IProfile profile, boolean current) {
        return false;
    }
}
