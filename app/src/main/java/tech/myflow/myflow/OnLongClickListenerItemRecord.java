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
public class OnLongClickListenerItemRecord implements View.OnLongClickListener{
    Context context;
    String id;

    @Override
    public boolean onLongClick(View view) {
        context = view.getContext();
        id = view.getTag().toString();

        final CharSequence[] items = { "Edit", "Delete" };

        new AlertDialog.Builder(context).setTitle("Item Record")
                .setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        if (item == 0) {
                            editRecord(Integer.parseInt(id));
                        }else if (item == 1) {

                            boolean deleteSuccessful = new TableControllerItem(context).delete(Integer.parseInt(id));

                            if (deleteSuccessful){
                                Toast.makeText(context, "Item was deleted.", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(context, "Unable to delete item.", Toast.LENGTH_SHORT).show();
                            }

                            ((MainActivity) context).countRecords();
                            ((MainActivity) context).readRecords();

                        }
                        dialog.dismiss();

                    }
                }).show();
        return false;
    }

    public void editRecord(final int itemId) {
        final TableControllerItem tableControllerItem = new TableControllerItem(context);
        ObjectItem objectItem = tableControllerItem.readSingleRecord(itemId);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.item_main_form, null, false);

        final EditText editTextItemName = (EditText) formElementsView.findViewById(R.id.editTextItemName);
        final EditText editTextItemAmount = (EditText) formElementsView.findViewById(R.id.editTextItemAmount);

        editTextItemName.setText(objectItem.item_name);
        editTextItemAmount.setText(objectItem.item_amount);

        new AlertDialog.Builder(context)
                .setView(formElementsView)
                .setTitle("Edit Record")
                .setPositiveButton("Save Changes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            ObjectItem objectItem = new ObjectItem();
                            objectItem.id = itemId;
                            objectItem.item_name = editTextItemName.getText().toString();
                            objectItem.item_amount = editTextItemAmount.getText().toString();

                            boolean updateSuccessful = tableControllerItem.update(objectItem);

                            if(updateSuccessful) {
                                Toast.makeText(context, "Item record was updated.", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(context, "Unable to item record.", Toast.LENGTH_SHORT).show();
                            }

                            ((MainActivity) context).countRecords();
                            ((MainActivity) context).readRecords();
                            dialog.cancel();
                        }

                    }).show();
    }

}
