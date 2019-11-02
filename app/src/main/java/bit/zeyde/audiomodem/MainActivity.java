package bit.zeyde.audiomodem;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SpinnerAdapter;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main";
    private EditText editText = null;
    private ImageButton recvBtn = null;
    private ImageButton sendBtn = null;
    private Color origColor = null;

    private Sender tx = null;
    private Receiver rx = null;
    private ProgressBar pBar;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editText);
        recvBtn = (ImageButton) findViewById(R.id.recvBtn);
        sendBtn = (ImageButton) findViewById(R.id.sendBtn);
        pBar = (ProgressBar) findViewById(R.id.pBar);

        // Get intent, action and MIME type
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        String str = "";
        Log.d(TAG, "type: " + type);

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            switch (type) {
                case "text/plain":
                    str = intent.getStringExtra(Intent.EXTRA_TEXT);
                    break;
            }
        }

        Log.d(TAG, "str: " + str);
        editText.setText(str);
        editText.setSelection(str.length());

        context = getApplicationContext();
    }

    void toast(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    void setText(String s) {
        editText.setText(s);
        editText.setSelection(s.length());
    }

    public void onReceive(View v) {
        rx = new Receiver() {
            @Override
            protected void onPostExecute(Result res) {
                if (res.err == null) {
                    setText(res.out);
                    toast("OK");
                } else {
                    setText("");
                    toast("Error: " + res.err);
                }
                recvBtn.setEnabled(true);
            }

            @Override
            protected void onProgressUpdate(Double... values) {
                double p = values[0];
                pBar.setProgress((int)(p * pBar.getMax()));
            }
        };
        recvBtn.setEnabled(false);
        rx.execute();
        toast("Receiving...");
    }

    public void onSend(View v) {
        String msg = editText.getText().toString();
        tx = new Sender() {
            @Override
            protected void onPostExecute(Void result) {
                sendBtn.setEnabled(true);
            }

            @Override
            protected void onProgressUpdate(Double... values) {
                double p = values[0];
                pBar.setProgress((int)(p * pBar.getMax()));
            }
        };
        sendBtn.setEnabled(false);
        tx.execute(msg);
        toast("Sending...");
    }

    public void onStop(View v) {
        setText("");
        if (rx != null) {
            rx.stop();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String str = editText.getText().toString();

        switch (item.getItemId()) {
            case R.id.menu_copy:
                ClipData clip = ClipData.newPlainText("text", str);
                ClipboardManager mgr = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                mgr.setPrimaryClip(clip);
                return true;

            case R.id.menu_share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, str);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                return true;

            case R.id.menu_about:
                toast("Written by Roman Zeyde\n(roman.zeyde@gmail.com)");
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void setCustomView(View view) {

    }


    public void setCustomView(View view, ActionBar.LayoutParams layoutParams) {

    }


    public void setCustomView(int resId) {

    }


    public void setIcon(int resId) {

    }


    public void setIcon(Drawable icon) {

    }


    public void setLogo(int resId) {

    }


    public void setLogo(Drawable logo) {

    }


    public void setListNavigationCallbacks(SpinnerAdapter adapter, ActionBar.OnNavigationListener callback) {

    }


    public void setSelectedNavigationItem(int position) {

    }


    public int getSelectedNavigationIndex() {
        return 0;
    }

    public int getNavigationItemCount() {
        return 0;
    }

    @Override
    public void setTitle(CharSequence title) {

    }

    @Override
    public void setTitle(int resId) {

    }


    public void setSubtitle(CharSequence subtitle) {

    }


    public void setSubtitle(int resId) {

    }

    public void setDisplayOptions(int options) {

    }


    public void setDisplayOptions(int options, int mask) {

    }


    public void setDisplayUseLogoEnabled(boolean useLogo) {

    }


    public void setDisplayShowHomeEnabled(boolean showHome) {

    }


    public void setDisplayHomeAsUpEnabled(boolean showHomeAsUp) {

    }


    public void setDisplayShowTitleEnabled(boolean showTitle) {

    }


    public void setDisplayShowCustomEnabled(boolean showCustom) {

    }


    public void setBackgroundDrawable(@Nullable Drawable d) {

    }


    public View getCustomView() {
        return null;
    }



    public CharSequence getSubtitle() {
        return null;
    }


    public int getNavigationMode() {
        return 0;
    }


    public void setNavigationMode(int mode) {

    }


    public int getDisplayOptions() {
        return 0;
    }


    public ActionBar.Tab newTab() {
        return null;
    }

    public void addTab(ActionBar.Tab tab) {

    }


    public void addTab(ActionBar.Tab tab, boolean setSelected) {

    }


    public void addTab(ActionBar.Tab tab, int position) {

    }


    public void addTab(ActionBar.Tab tab, int position, boolean setSelected) {

    }


    public void removeTab(ActionBar.Tab tab) {

    }

    public void removeTabAt(int position) {

    }


    public void removeAllTabs() {

    }


    public void selectTab(ActionBar.Tab tab) {

    }


    public ActionBar.Tab getSelectedTab() {
        return null;
    }


    public ActionBar.Tab getTabAt(int index) {
        return null;
    }

    public int getTabCount() {
        return 0;
    }


    public int getHeight() {
        return 0;
    }


    public void show() {

    }


    public void hide() {

    }


    public boolean isShowing() {
        return false;
    }


    public void addOnMenuVisibilityListener(ActionBar.OnMenuVisibilityListener listener) {

    }


    public void removeOnMenuVisibilityListener(ActionBar.OnMenuVisibilityListener listener) {

    }
}
