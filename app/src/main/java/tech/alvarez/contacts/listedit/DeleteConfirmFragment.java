package tech.alvarez.contacts.listedit;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import tech.alvarez.contacts.R;
import tech.alvarez.contacts.utils.Constants;

public class DeleteConfirmFragment extends DialogFragment {

    private tech.alvarez.contacts.listedit.ListContract.DeleteListener mListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final long thingId = getArguments().getLong(Constants.THING_ID);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.are_you_sure);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mListener.setConfirm(true, thingId);
            }
        });

        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mListener.setConfirm(false, thingId);
            }
        });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (tech.alvarez.contacts.listedit.ListContract.DeleteListener) context;
    }
}
