package com.example.android.calculator20;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    private Button sinBtn;
    private Button cosBtn;
    private Button tanBtn;
    private Button deleteBtn;

    private Button expBtn;
    private Button logBtn;
    private Button modBtn;
    private Button pointBtn;

    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button divBtn;

    private Button btn4;
    private Button btn5;
    private Button btn6;
    private Button mulBtn;

    private Button btn7;
    private Button btn8;
    private Button btn9;
    private Button subBtn;

    private Button clearBtn;
    private Button btn0;
    private Button addBtn;
    private Button resBtn;

    private static boolean typeable = true;
    private static boolean floatingPointNumber = false;
    private static boolean operationEnable = false;

    private Double leftOperator;
    private Double rightOperator;
    private Enum<BinaryOperation> op;

    private BinaryOperation currentOperation;

    private class DigitOnClickListener implements View.OnClickListener {

        private int digit;

        public DigitOnClickListener(int digit) {
            this.digit = digit;
        }

        @Override
        public void onClick(View v) {
            String text = "";
            textView.setHint("");
            if (typeable) {
                text = (String) textView.getText();
                text = text + digit;
            }

            textView.setText(text);
            typeable = true;
            operationEnable = true;
        }
    }

    private static class UnaryOperatorOnClickListener implements View.OnClickListener {

        UnaryOperator op;

        TextView textView;

        public interface UnaryOperator {
            double operation(double a);
        }

        public UnaryOperatorOnClickListener(UnaryOperator op, TextView textView) {
            this.op = op;
            this.textView = textView;
        }

        @Override
        public void onClick(View v) {
            textView.setHint("");
            if (operationEnable) {
                String text = (String) textView.getText();
                if (floatingPointNumber) {
                    double d = Double.parseDouble(text);
                    d = op.operation(d);
                    text = d + "";
                    textView.setText(text);
                } else {
                    int parseInt = Integer.parseInt(text);
                    double d = op.operation(parseInt);
                    text = d + "";
                    textView.setText(text);
                }
                typeable = false;
                floatingPointNumber = true;
            }
        }
    }

    private class BinaryOperatorOnClickListener implements View.OnClickListener {

        private BinaryOperation operation;

        public BinaryOperation getOperation() {
            return operation;
        }

        public BinaryOperatorOnClickListener(BinaryOperation operation) {
            this.operation = operation;
        }

        @Override
        public void onClick(View v) {
            if (operationEnable) {
                String text = (String) textView.getText();
                if (floatingPointNumber) {
                    double parseDouble = Double.parseDouble(text);
                    leftOperator = parseDouble;
                } else {
                    int parseInt = Integer.parseInt(text);
                    leftOperator = (double) parseInt;
                }
                currentOperation = (BinaryOperation) operation;
                textView.setText("");
                typeable = true;
                floatingPointNumber = false;
                operationEnable = false;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView_id);

        sinBtn = (Button) findViewById(R.id.sinBtn);
        cosBtn = (Button) findViewById(R.id.cosBtn);
        tanBtn = (Button) findViewById(R.id.tanBtn);

        expBtn = (Button) findViewById(R.id.expBtn);
        logBtn = (Button) findViewById(R.id.logBtn);
        modBtn = (Button) findViewById(R.id.modBtn);
        pointBtn = (Button) findViewById(R.id.pointBtn);

        btn1 = (Button) findViewById(R.id.oneBtn);  //
        btn2 = (Button) findViewById(R.id.twoBtn);  //
        btn3 = (Button) findViewById(R.id.threeBtn);//
        divBtn = (Button) findViewById(R.id.divBtn);

        btn4 = (Button) findViewById(R.id.fourBtn);//
        btn5 = (Button) findViewById(R.id.fiveBtn); //
        btn6 = (Button) findViewById(R.id.sixBtn);  //
        mulBtn = (Button) findViewById(R.id.mulBtn);

        btn7 = (Button) findViewById(R.id.sevenBtn);//
        btn8 = (Button) findViewById(R.id.eightBtn);//
        btn9 = (Button) findViewById(R.id.nineBtn); //
        subBtn = (Button) findViewById(R.id.subBtn);

        clearBtn = (Button) findViewById(R.id.clearBtn);
        btn0 = (Button) findViewById(R.id.zeroBtn); //
        addBtn = (Button) findViewById(R.id.addBtn);

        deleteBtn = (Button) findViewById(R.id.delete_id);//
        resBtn = (Button) findViewById(R.id.res_id);

        String text = "";

        Button[] digitButtons = new Button[]{btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9};

        for (int i = 0; i < 10; i++) {
            digitButtons[i].setOnClickListener(new DigitOnClickListener(i));
        }

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = (String) textView.getText();
                if (text.length() > 0) {
                    try {
                        // you should optimize this method
                        if (text.charAt(text.length() - 1) == '.') {
                            floatingPointNumber = false;
                            operationEnable = true;
                        }

                        text = text.substring(0, text.length() - 1);
                        textView.setText(text);
                        if (text.charAt(text.length() - 1) == '.') {
                            operationEnable = false;
                        }
                    } catch (IndexOutOfBoundsException e) {

                    }
                }
                if (text.length() == 0) {
                    floatingPointNumber = false;
                    operationEnable = false;
                    typeable = true;
                }
            }
        });

        pointBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = (String) textView.getText();
                if (text.length() > 0 && !floatingPointNumber) {
                    text = text + '.';
                    textView.setText(text);
                    typeable = true;
                    floatingPointNumber = true;
                    operationEnable = false;
                }
            }
        });

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("");
                typeable = true;
                operationEnable = false;
                floatingPointNumber = false;
            }
        });


        sinBtn.setOnClickListener(new UnaryOperatorOnClickListener((double a) -> Math.sin(a), textView));

        cosBtn.setOnClickListener(new UnaryOperatorOnClickListener((double a) -> Math.cos(a), textView));

        tanBtn.setOnClickListener(new UnaryOperatorOnClickListener((double a) -> Math.tan(a), textView));

        expBtn.setOnClickListener(new UnaryOperatorOnClickListener((double a) -> Math.exp(a), textView));

        logBtn.setOnClickListener(new UnaryOperatorOnClickListener((double a) -> Math.log(a), textView));


        addBtn.setOnClickListener(new BinaryOperatorOnClickListener(BinaryOperation.ADD));

        subBtn.setOnClickListener(new BinaryOperatorOnClickListener(BinaryOperation.SUB));

        mulBtn.setOnClickListener(new BinaryOperatorOnClickListener(BinaryOperation.MUL));

        divBtn.setOnClickListener(new BinaryOperatorOnClickListener(BinaryOperation.DIV));

        modBtn.setOnClickListener(new BinaryOperatorOnClickListener(BinaryOperation.MOD));


        resBtn.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (operationEnable && leftOperator != null) {
                    String text = (String) textView.getText();
                    if (floatingPointNumber) {
                        double parseDouble = Double.parseDouble(text);
                        rightOperator = parseDouble;
                    } else {
                        int parseInt = Integer.parseInt(text);
                        rightOperator = (double) parseInt;
                    }
                    double res = 0;
                    switch (currentOperation) {
                        case ADD:
                            res = leftOperator + rightOperator;
                            break;
                        case SUB:
                            res = leftOperator - rightOperator;
                            break;
                        case MUL:
                            res = leftOperator * rightOperator;
                            break;
                        case DIV:
                            if (rightOperator != 0) {
                                res = leftOperator / rightOperator;
                            } else {
                                text = "Division by Zero";
                                operationEnable = false;
                                typeable = true;
                                floatingPointNumber = false;
                                textView.setText("");
                                textView.setHint(text);
                                return;
                            }
                            break;
                        case MOD:
                            res = leftOperator % rightOperator;
                            break;
                        default:
                            throw new RuntimeException();
                    }
                    text = res + "";
                    floatingPointNumber = typeable = operationEnable = true;
                    if (res == (int) res) {
                        text = text.substring(0, text.length() - 2);
                        floatingPointNumber = false;
                    }
                    textView.setText(text);
                    rightOperator = null;
                    currentOperation = null;
                }
            }
        }));
    }
}