package kmitl.lab03.weerabhat58070128.simplemydot.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.larswerkman.holocolorpicker.ColorPicker;

import kmitl.lab03.weerabhat58070128.simplemydot.R;
import kmitl.lab03.weerabhat58070128.simplemydot.model.Dot;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditDotFragment extends Fragment implements View.OnClickListener {

    public interface EditDotFragmentListener {
        void onCancelPressed();
        void onConfirmPressed(Dot dot, int index);
    }

    private static final String KEY_DOT = "dot";
    private static final String KEY_INDEX = "index";

    private EditDotFragmentListener listener;
    private ColorPicker colorPicker;
    private EditText editTextX;
    private EditText editTextY;
    private EditText editTextSize;
    private Button btnCancel;
    private Button btnConfirm;
    private Dot dot;
    private int index;

    public static EditDotFragment newInstance(Dot dot, int index) {
        Bundle args = new Bundle();
        args.putParcelable(KEY_DOT, dot);
        args.putInt(KEY_INDEX, index);
        EditDotFragment fragment = new EditDotFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dot = getArguments().getParcelable(KEY_DOT);
        index = getArguments().getInt(KEY_INDEX);
        listener = (EditDotFragmentListener) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_edit_dot, container, false);

        colorPicker = (ColorPicker) rootView.findViewById(R.id.colorPicker);
        editTextX = (EditText) rootView.findViewById(R.id.editTextX);
        editTextY = (EditText) rootView.findViewById(R.id.editTextY);
        editTextSize = (EditText) rootView.findViewById(R.id.editTextSize);
        btnCancel = (Button) rootView.findViewById(R.id.btnCancel);
        btnConfirm = (Button) rootView.findViewById(R.id.btnConfirm);

        colorPicker.setOldCenterColor(dot.getColor());
        editTextX.setText(Integer.toString(dot.getCenterX()));
        editTextY.setText(Integer.toString(dot.getCenterY()));
        editTextSize.setText(Integer.toString(dot.getRadius()));
        btnCancel.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);

        return rootView;
    }

    private void onCancel() {
        listener.onCancelPressed();
    }

    private void onConfirm() {
        try {
            int centerX = Integer.parseInt(editTextX.getText().toString());
            int centerY = Integer.parseInt(editTextY.getText().toString());
            int radius = Integer.parseInt(editTextSize.getText().toString());
            int color = colorPicker.getColor();
            listener.onConfirmPressed(new Dot(centerX, centerY, radius, color), index);
        } catch (NumberFormatException e) {
            Toast.makeText(getActivity(), "Please enter the number", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCancel:
                onCancel();
                break;
            case R.id.btnConfirm:
                onConfirm();
                break;
        }
    }
}
