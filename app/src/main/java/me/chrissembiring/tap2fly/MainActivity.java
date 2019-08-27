package me.chrissembiring.tap2fly;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity
{
    //NFC details
    NfcAdapter nfcAdapter;
    PendingIntent pendingIntent;
    Tag tag;

    //Text fields
    TextView txtName, txtSeat, txtFlightNo;
    EditText txtNameEdit, txtSeatnoEdit, txtFlightNoEdit;
    ListView lvInfo;

    //Database details
    private DatabaseHandler db;
    ArrayList<String> ArrayofName = new ArrayList<String>();
    List<FlightManifest> flightmanifest;
    static ArrayAdapter<String> dbAdapter;

    //Starting application
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Assigning text variables
        txtName = (TextView) findViewById(R.id.txtName);
        txtSeat = (TextView) findViewById(R.id.txtSeat);
        txtFlightNo = (TextView) findViewById(R.id.txtFlightNo);
        txtNameEdit = (EditText) findViewById(R.id.txtNameEdit);
        txtSeatnoEdit = (EditText) findViewById(R.id.txtSeatNoEdit);
        txtFlightNoEdit = (EditText) findViewById(R.id.txtFlightnoEdit);
        lvInfo = (ListView) findViewById(R.id.listPassenger);

        //Assigning NFC adapter and intent
        NfcManager manager = (NfcManager) this.getSystemService(Context.NFC_SERVICE);
        nfcAdapter = manager.getDefaultAdapter();
        pendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this,getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

        //Assigning database adapter
        db = new DatabaseHandler(this);
        dbAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                android.R.id.text1, ArrayofName);
        lvInfo.setAdapter(dbAdapter);

        lvInfo.setOnItemClickListener(new OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                txtNameEdit.setText(flightmanifest.get(position).getName());
                txtSeatnoEdit.setText(flightmanifest.get(position).getSeatno());
                txtFlightNoEdit.setText(flightmanifest.get(position).getFlightno());
            }
        });
        DisplayAll();
    }

    //Pausing application
    @Override
    protected void onPause()
    {
        super.onPause();
        nfcAdapter.disableForegroundDispatch(this);
    }

    //Resuming application
    @Override
    protected void onResume()
    {
        super.onResume();
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null);
    }

    //Detecting tag
    @Override
    protected void onNewIntent(Intent intent)
    {
        tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        Toast.makeText(getBaseContext(), "Tag detected!", Toast.LENGTH_LONG).show();
    }

    //Writing tag
    public void WriteText(View v)
    {
        try
        {
            if(tag != null)
            {
                if(txtNameEdit.getText().length() == 0 || txtSeatnoEdit.getText().length() == 0 ||
                        txtFlightNoEdit.getText().length() == 0)
                {
                    Toast.makeText(this, "Please enter name and seat number", Toast.LENGTH_LONG).show();
                }

                else
                {
                    //NFC Action
                    NdefRecord txtRecordName = createTextRecord
                            (txtNameEdit.getText().toString(), Locale.ENGLISH, true);
                    NdefRecord txtRecordSeatno = createTextRecord
                            (txtSeatnoEdit.getText().toString(), Locale.ENGLISH, true);
                    NdefRecord txtRecordFlightno = createTextRecord
                            (txtFlightNoEdit.getText().toString(), Locale.ENGLISH, true);
                    NdefRecord[] ndefRecords = {txtRecordName, txtRecordSeatno};
                    write(tag, ndefRecords);

                    //Database Action
                    FlightManifest fm = new FlightManifest(txtNameEdit.getText().toString(),
                            txtSeatnoEdit.getText().toString(), null, 0, 0, 0);
                    db.addFlightManifest(fm);
                    DisplayAll();
                    Toast.makeText(this, "Passenger Info Updated!", Toast.LENGTH_LONG).show();

                }
            }

            else
                Toast.makeText(getBaseContext(), "No tag detected!", Toast.LENGTH_LONG).show();
        }

        catch (IOException e)
        {
            e.printStackTrace();
        }

        catch (FormatException e)
        {
            e.printStackTrace();
        }
    }

    public void deleteManifest(View v)
    {
        if ((txtNameEdit.getText().length() == 0) || (txtSeatnoEdit.getText().length() == 0))
        {
            Toast.makeText(this, "Please select passenger name to be deleted", Toast.LENGTH_LONG).show();
        }

        else
        {
            FlightManifest fm = new FlightManifest(
                    txtNameEdit.getText().toString(),
                    txtSeatnoEdit.getText().toString(),
                    null,
                    0,
                    0,
                    0);
            db.deleteFlightManifest(fm);
            DisplayAll();
            Toast.makeText(this, "Contact Deleted!", Toast.LENGTH_LONG).show();
        }
    }

    private void write(Tag tag, NdefRecord[] ndefRecords) throws IOException, FormatException
    {
        NdefMessage ndefMessage = new NdefMessage(ndefRecords);
        Ndef ndef = Ndef.get(tag);

        try
        {
            if (ndef != null)
            {
                ndef.connect();
                ndef.writeNdefMessage(ndefMessage);
                ndef.close();
                Toast.makeText(this, "Tag written!", Toast.LENGTH_LONG).show();
            }

            else
            {
                Toast.makeText(this, "Tag is not NDEF supported", Toast.LENGTH_LONG).show();
            }
        }

        catch (Exception e)
        {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public NdefRecord createTextRecord (String payload, Locale locale, boolean encodeInUtf8)
    {
        byte[] langBytes = locale.getLanguage().getBytes(Charset.forName("US-ASCII"));
        Charset utfEncoding = encodeInUtf8 ? Charset.forName("UTF-8") : Charset.forName("UTF-16");
        byte[] textBytes = payload.getBytes(utfEncoding);
        int utfBit = encodeInUtf8 ? 0 : (1<<7);
        char status = (char) (utfBit + langBytes.length);

        byte[] data = new byte[1 + langBytes.length + textBytes.length];
        data[0] = (byte) status;
        System.arraycopy(langBytes, 0, data, 1, langBytes.length);
        System.arraycopy(textBytes, 0, data, 1 + langBytes.length, textBytes.length);

        NdefRecord record = new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], data);
        return record;
    }

    //Flight Manifest
    public void DisplayAll()
    {
        flightmanifest = db.getAllFlightManifest();
        ArrayofName.clear();
        for (FlightManifest fm : flightmanifest)
        {
            ArrayofName.add(fm.getId() + ".\t" + fm.getName() + ".\t" + fm.getSeatno());
        }
        dbAdapter.notifyDataSetChanged();
        txtNameEdit.setText("");
        txtSeatnoEdit.setText("");
    }


}