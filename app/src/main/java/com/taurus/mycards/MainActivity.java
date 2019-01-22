package com.taurus.mycards;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.MifareUltralight;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcB;
import android.nfc.tech.NfcBarcode;
import android.nfc.tech.NfcF;
import android.nfc.tech.NfcV;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private PendingIntent mNfcPendingIntent;
    private IntentFilter[] mNfcIntentFiltersArray;
    private String[][] mNfcTechListsArray;
    private NfcAdapter mNfcAdapter;
    private TextView nfcContentViewer;
    private StringBuilder contentBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       
        //初始化NFC
        initView();
        nfcCheck();
        initNfcObject();
        

    }
    //初始化视图
    private void initView(){
        nfcContentViewer = findViewById(R.id.nfc_content_view);
        nfcContentViewer.setText("scan a tag");
        contentBuilder = new StringBuilder();
    }

    //初始化NFC
    private void initNfcObject() {
        //初始化NfC
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        mNfcPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        try {
            //Handles all MIME based dispatchers.
            // You should specify only the ones that you need.
            ndef.addDataType("*/*");

        } catch (IntentFilter.MalformedMimeTypeException e) {
            throw new RuntimeException("fail", e);
        }
        mNfcIntentFiltersArray = new IntentFilter[]{ndef,};
        mNfcTechListsArray = new String[][]{new String[]{
                IsoDep.class.getName()
                , MifareClassic.class.getName()
                , MifareUltralight.class.getName()
                , NdefFormatable.class.getName()
                , NfcA.class.getName()
                , Ndef.class.getName()
                , NfcB.class.getName()
                , NfcBarcode.class.getName()
                , NfcF.class.getName()
                , NfcV.class.getName()
        }};

    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mNfcAdapter != null) mNfcAdapter.disableForegroundDispatch(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mNfcAdapter != null) mNfcAdapter.enableForegroundDispatch(this, mNfcPendingIntent, mNfcIntentFiltersArray,
                mNfcTechListsArray);
    }

    @Override
    public void onNewIntent(Intent intent) {
        Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        tagFromIntent.getTechList();
        tagFromIntent.getId();
        tagFromIntent.toString();

        contentBuilder.append("techList:").append(tagFromIntent.getTechList()).append("\n")
                .append("id:").append(tagFromIntent.getId()).append("\n")
                .append("String:").append(tagFromIntent.toString()).append("\n");
        nfcContentViewer.setText(contentBuilder.toString());
        //todo somethine with tagFromIntent
    }


    /**
     * 检测是否支持 NFC
     */
    private void nfcCheck() {
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mNfcAdapter == null) {
            String info = "手机不支付NFC功能";
            toast(info);
            return;
        }
        if (!mNfcAdapter.isEnabled()) {
            String info = "手机NFC功能没有打开";
            toast(info);
            Intent setNfc = new Intent(Settings.ACTION_NFC_SETTINGS);
            startActivity(setNfc);
        } else {
            String info = "手机NFC功能正常";
            toast(info);
        }
    }
}
