package tech.alvarez.contacts.edit;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import tech.alvarez.contacts.R;
import tech.alvarez.contacts.data.db.AppDatabase;
import tech.alvarez.contacts.data.db.converter.ImageConverter;
import tech.alvarez.contacts.data.db.entity.Thing;
import tech.alvarez.contacts.utils.Constants;


public class EditActivity extends AppCompatActivity implements EditContract.View {

    private EditContract.Presenter mPresenter;

    private EditText mNameEditText;
    private EditText mSupplierEditText;
    private EditText mQuantityEditText;
    private EditText mPriceEditText;
    private ImageView mImage;


    private TextInputLayout mNameTextInputLayout;
    private TextInputLayout mSupplierInputLayout;
    private TextInputLayout mQuantityInputLayout;
    private TextInputLayout mPriceTextInputLayout;
    private Button mButton;

    private FloatingActionButton mFab;

    private ImageConverter toBitmap;

    private static final int CAMERA_REQUEST = 1888;

    private Thing person;
    private boolean mEditMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        person = new Thing();
        checkMode();

        AppDatabase db = AppDatabase.getDatabase(getApplication());
        mPresenter = new EditPresenter(this, db.personModel());

        initViews();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mEditMode) {
            mPresenter.getPersonAndPopulate(person.id);
        }
    }

    private void checkMode() {
        if (getIntent().getExtras() != null) {
            person.id = getIntent().getLongExtra(Constants.THING_ID, 0);
            mEditMode = true;
        }
    }

    private void initViews() {
        mNameEditText = (EditText) findViewById(R.id.nameEditText);
        mSupplierEditText = (EditText) findViewById(R.id.supplierEditText);
        mQuantityEditText = (EditText) findViewById(R.id.quantityEditText);
        mPriceEditText = (EditText) findViewById(R.id.priceEditText);
        mImage = (ImageView) findViewById(R.id.imageid);

        mNameTextInputLayout = (TextInputLayout) findViewById(R.id.nameTextInputLayout);
        mSupplierInputLayout = (TextInputLayout) findViewById(R.id.supplierTextInputLayout);
        mQuantityInputLayout = (TextInputLayout) findViewById(R.id.quantityTextInputLayout);
        mPriceTextInputLayout = (TextInputLayout) findViewById(R.id.priceTextInputLayout);


        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setImageResource(mEditMode ? R.drawable.ic_refresh : R.drawable.ic_done);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                person.thingName = mNameEditText.getText().toString();
                person.supplier = mSupplierEditText.getText().toString();
                person.quantity = mQuantityEditText.getText().toString();
                person.price = mPriceEditText.getText().toString();
//                person.image = mImage.setImageBitmap(phi);

                boolean valid = mPresenter.validate(person);

                if (!valid) return;

                if (mEditMode) {
                    mPresenter.update(person);
                } else {
                    mPresenter.save(person);
                }
            }
        });
    }



    @Override
    public void setPresenter(EditContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showErrorMessage(int field) {
        if (field == Constants.FIELD_NAME) {
            mNameTextInputLayout.setError(getString(R.string.invalid_name));
        } else if (field == Constants.FIELD_QUANTITY) {
            mQuantityInputLayout.setError(getString(R.string.invalid_email));
        } else if (field == Constants.FIELD_PRICE) {
            mPriceTextInputLayout.setError(getString(R.string.invalid_phone));
        } else if (field == Constants.FIELD_SUPPLIER) {
            mSupplierInputLayout.setError(getString(R.string.invalid_supplier));
        }
    }

    @Override
    public void clearPreErrors() {
        mNameTextInputLayout.setErrorEnabled(false);
        mQuantityInputLayout.setErrorEnabled(false);
        mPriceTextInputLayout.setErrorEnabled(false);
        mSupplierInputLayout.setErrorEnabled(false);

    }


    @Override
    public void close() {
        finish();
    }

    @Override
    public void populate(Thing person) {
        this.person = person;
        mNameEditText.setText(person.thingName);
        mSupplierEditText.setText(person.supplier);
        mQuantityEditText.setText(person.quantity);
        mPriceEditText.setText(person.price);
    }

    @Override
    public void takePhoto() {
        mButton = (Button) findViewById(R.id.takePhotoid);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePhotoFormButton();
            }
        });
    }

    public void takePhotoFormButton() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            Toast.makeText(this,"error", Toast.LENGTH_SHORT).show();
            startActivityForResult(intent, CAMERA_REQUEST);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK)
        {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            mImage.setImageBitmap(photo);
        }
    }

}
