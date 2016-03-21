package br.edu.nutrif.activitys;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;

import java.util.List;

import br.edu.nutrif.R;
import br.edu.nutrif.adapters.HorarioAdapter;
import br.edu.nutrif.callback.CallbackButton;
import br.edu.nutrif.database.dao.AlunoDAO;
import br.edu.nutrif.entitys.Aluno;
import br.edu.nutrif.entitys.Dia;
import br.edu.nutrif.entitys.DiaRefeicao;
import br.edu.nutrif.entitys.PretencaoRefeicao;
import br.edu.nutrif.entitys.Refeicao;
import br.edu.nutrif.entitys.input.Id;
import br.edu.nutrif.network.ConnectionServer;
import br.edu.nutrif.qrcode.Contents;
import br.edu.nutrif.qrcode.QRCodeEncoder;
import br.edu.nutrif.util.AndroidUtil;
import br.edu.nutrif.util.PreferencesUtils;
import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class RefeitorioActivity extends AppCompatActivity implements CallbackButton {
    @Bind(R.id.loadinglayout)
    LinearLayout loadLayout;

    @Bind(R.id.recycle)
    RecyclerView recycle;

    @Bind(R.id.codelayout)
    LinearLayout codelayout;

    List<DiaRefeicao> refeicoes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refeitorio);
        ButterKnife.bind(this);
        String matricula = AlunoDAO.getInstance(this).find().getMatricula();
        String auth = PreferencesUtils.getAccessKeyOnSharedPreferences(this);
        Call<List<DiaRefeicao>> call = ConnectionServer.getInstance().getService().listaRefeicoes(auth,
         matricula);
        call.enqueue(new Callback<List<DiaRefeicao>>() {
            @Override
            public void onResponse(Response<List<DiaRefeicao>> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    refeicoes = response.body();
                    RefeitorioActivity.this.montaTabela(refeicoes);
                } else {
                    AndroidUtil.showSnackbar(RefeitorioActivity.this, R.string.impossivelcarregar);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                AndroidUtil.showSnackbar(RefeitorioActivity.this, R.string.impossivelcarregar);
                loadLayout.setVisibility(View.GONE);
            }
        });
    }

    public void montaTabela(List<DiaRefeicao> refeicoes) {
        loadLayout.setVisibility(View.GONE);
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(this);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recycle.setLayoutManager(gridLayoutManager);
        recycle.setAdapter(new HorarioAdapter(this, refeicoes, this));

    }

    @Override
    public void onClickCallback(View view, int position) {
        DiaRefeicao refeicao = refeicoes.get(position);
        String matricula = AlunoDAO.getInstance(this).find().getMatricula();

        PretencaoRefeicao pretencao = new PretencaoRefeicao();
        pretencao.getDiaRefeicao().setAluno(new Aluno(matricula, null, null));
        pretencao.getDiaRefeicao().setDia(new Dia(refeicao.getDia().getId(), null));
        pretencao.getDiaRefeicao().setRefeicao(new Refeicao(refeicao.getRefeicao().getId()));

        Call<PretencaoRefeicao> call = ConnectionServer
                .getInstance()
                .getService()
                .pedirRefeicao(PreferencesUtils.getAccessKeyOnSharedPreferences(this), pretencao);
        call.enqueue(new Callback<PretencaoRefeicao>() {
            @Override
            public void onResponse(Response<PretencaoRefeicao> response, Retrofit retrofit) {
                if(response.isSuccess()){
                    gerandoQrcode(response.body().getKeyAccess());
                }else {
                    AndroidUtil.showSnackbar(RefeitorioActivity.this, R.string.undefinedError);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                AndroidUtil.showSnackbar(RefeitorioActivity.this, R.string.erroconexao);
            }
        });

    }

    public void gerandoQrcode(String str){
        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        int smallerDimension = width < height ? width : height;
        smallerDimension = smallerDimension * 3/4;

        //Encode with a QR Code image
        QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(str,
                null,
                Contents.Type.TEXT,
                BarcodeFormat.QR_CODE.toString(),
                smallerDimension);
        try {
            Bitmap bitmap = qrCodeEncoder.encodeAsBitmap();
            ImageView myImage = (ImageView) findViewById(R.id.code);
            myImage.setImageBitmap(bitmap);
            codelayout.setVisibility(View.VISIBLE);
            recycle.setVisibility(View.GONE);

        } catch (WriterException e) {
            AndroidUtil.showSnackbar(this, R.string.errocode);
        }

    }

    public void voltarCodigo(View view){
        codelayout.setVisibility(View.GONE);
        recycle.setVisibility(View.VISIBLE);
    }
}
