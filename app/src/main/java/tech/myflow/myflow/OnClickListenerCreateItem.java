package tech.myflow.myflow;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by android on 3/20/2017.
 */
public class OnClickListenerCreateItem implements View.OnClickListener {
    @Override
    public void onClick(View view) {
        final Context context = view.getContext();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.item_main_form, null, false);

        final EditText editTextItemName = (EditText) formElementsView.findViewById(R.id.editTextItemName);
        final EditText editTextItemAmount = (EditText) formElementsView.findViewById(R.id.editTextItemAmount);

        new AlertDialog.Builder(context)
                .setView(formElementsView)
                .setTitle("Create Item")
                .setPositiveButton("Add",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String itemName = editTextItemName.getText().toString();
                                String itemAmount = editTextItemAmount.getText().toString();

                                ObjectItem objectItem = new ObjectItem();
                                objectItem.item_name= itemName;
                                objectItem.item_amount= itemAmount;

                                boolean createSuccessful = new TableControllerItem(context).create(objectItem);

                                if(createSuccessful){
                                    Toast.makeText(context, "Item was saved.", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(context, "Unable to save item.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).show();
    }
}
