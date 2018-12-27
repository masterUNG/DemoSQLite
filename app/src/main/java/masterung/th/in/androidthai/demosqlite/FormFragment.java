package masterung.th.in.androidthai.demosqlite;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FormFragment extends Fragment {

    //    Explicit
    private String nameString, surnameString, genderString,
            ageString, allergicString = "0", detailString;
    private boolean ageABoolean = true; // true Not Choose Age

    public FormFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Gender Controller
        genderController();

//        Save Controller
        saveController();

//        Age Controller
        ageController();

//        Allergic Controller
        allergicController();

    }   // Main Method

    private void allergicController() {
        final CheckBox checkBox = getView().findViewById(R.id.chbAllergic);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()) {
                    allergicString = "1";
                } else {
                    allergicString = "0";
                }
            }
        });
    }

    private void ageController() {

        final String[] strings = new String[]{"Please Choose Age", "0 - 10", "11 - 20", "21 - 30", "31 - 40", "41 - 50", "Over 51"};
        Spinner spinner = getView().findViewById(R.id.spnAge);
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, strings);
        spinner.setAdapter(stringArrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ageString = strings[position];
                if (!(position == 0)) {
                    ageABoolean = false;
                } else {
                    ageABoolean = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void genderController() {
        RadioGroup radioGroup = getView().findViewById(R.id.ragGender);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {


                switch (checkedId) {
                    case R.id.radMale:
                        genderString = "0";
                        break;
                    case R.id.radFemale:
                        genderString = "1";
                        break;

                }

            }
        });
    }

    private void saveController() {
        Button button = getView().findViewById(R.id.btnSave);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Get Value From Edittext
                EditText nameEditText = getView().findViewById(R.id.edtName);
                EditText surnameEditText = getView().findViewById(R.id.edtSurname);
                EditText detailEditText = getView().findViewById(R.id.edtDetail);

                nameString = nameEditText.getText().toString().trim();
                surnameString = surnameEditText.getText().toString().trim();
                detailString = detailEditText.getText().toString().trim();

                MyAlert myAlert = new MyAlert(getActivity());

                RadioButton maleRadioButton = getView().findViewById(R.id.radMale);
                RadioButton femaleRadioButton = getView().findViewById(R.id.radFemale);

//                Check Space
                if (nameString.isEmpty() || surnameString.isEmpty() || detailString.isEmpty()) {
//                    Have Space
                    myAlert.normalDialog("Have Space", "Please Fill Every Blank");
                } else if (!(maleRadioButton.isChecked() || femaleRadioButton.isChecked())) {
//                        Non Choose Gender
                    myAlert.normalDialog("Non Gender", "Please Choose Male or Female");
                } else if (ageABoolean) {
                    myAlert.normalDialog("Non Choose Age", "Please Choose Age");
                } else {
                    confirmData();
                }


            }   // onClick
        });
    }

    private void confirmData() {

        String[] genderStrings = new String[]{"ชาย", "หญิง"};
        String[] allergisStrings = new String[]{"ไม่แพ้", "แพ้"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        builder.setTitle("Confirm Data");
        builder.setMessage("Name ==> " + nameString + "\n" +
                "Surname ==> " + surnameString + "\n" +
                "Gender ==> " + genderStrings[Integer.parseInt(genderString)] + "\n" +
                "Age ==> " + ageString + "\n" +
                "Allergis ==> " + allergisStrings[Integer.parseInt(allergicString)]);

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_form, container, false);
    }

}
