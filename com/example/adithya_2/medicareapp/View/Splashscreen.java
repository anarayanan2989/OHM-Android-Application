package com.example.adithya_2.medicareapp.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.example.adithya_2.medicareapp.C0211R;

public class Splashscreen extends Activity {
    Thread splashTread;

    /* renamed from: com.example.adithya_2.medicareapp.View.Splashscreen.1 */
    class C02121 extends Thread {
        C02121() {
        }

        public void run() {
            int waited = 0;
            while (waited < 3500) {
                try {
                    C02121.sleep(100);
                    waited += 100;
                } catch (InterruptedException e) {
                    Splashscreen.this.finish();
                    return;
                } catch (Throwable th) {
                    Splashscreen.this.finish();
                }
            }
            Intent intent = new Intent(Splashscreen.this, MainActivity.class);
            intent.setFlags(AccessibilityNodeInfoCompat.ACTION_CUT);
            Splashscreen.this.startActivity(intent);
            Splashscreen.this.finish();
            Splashscreen.this.finish();
        }
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        getWindow().setFormat(1);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0211R.layout.activity_splashscreen);
        StartAnimations();
    }

    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, C0211R.anim.alpha);
        anim.reset();
        LinearLayout l = (LinearLayout) findViewById(C0211R.id.lin_lay);
        l.clearAnimation();
        l.startAnimation(anim);
        anim = AnimationUtils.loadAnimation(this, C0211R.anim.translate);
        anim.reset();
        ImageView iv = (ImageView) findViewById(C0211R.id.splash);
        iv.clearAnimation();
        iv.startAnimation(anim);
        this.splashTread = new C02121();
        this.splashTread.start();
    }
}
