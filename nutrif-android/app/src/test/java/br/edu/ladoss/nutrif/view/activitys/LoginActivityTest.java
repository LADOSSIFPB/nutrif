package br.edu.ladoss.nutrif.view.activitys;

import android.app.Activity;
import android.widget.Button;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import br.edu.ladoss.nutrif.BuildConfig;
import static org.robolectric.Shadows.shadowOf;

/**
 * Created by Juan on 09/12/2016.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 23)
public class LoginActivityTest {

    @Test
    public void criacaoLogin() throws Exception {
        Activity activity = Robolectric.setupActivity(LoginActivity.class);
        //assertThat(results.getText().toString(), equalTo("Testing Android Rocks!"));
    }
}
