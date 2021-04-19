package com.example.kaunbanegacrorepati;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView question, option1, option2, option3, option4;
    private Button next;

    // These 5 variables are used during runtime memory
    private int questionNumber;
    private Boolean isSelected = false;
    private String selected = "";
    private int score = 0;
    private ArrayList<Question> questionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        question = findViewById(R.id.question);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        next = findViewById(R.id.nextQuestion);

        questionNumber = 0;
        questionList = new ArrayList<Question>();

        getJSON();

        generateQuestion(questionNumber);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isSelected) {
                    Toast.makeText(MainActivity.this, "Please select an option.", Toast.LENGTH_SHORT).show();
                } else {
                    isSelected = false;
                    Question questionClass = questionList.get(questionNumber);
                    questionClass.setResponse(selected);
                    if (selected.equals("A")) {
                        option1.setBackgroundResource(R.drawable.text_background);
                        if(option1.getText().toString().equals(questionClass.getCorrect_answer())){
                            score += 10;
                        }
                    } else if (selected.equals("B")) {
                        option2.setBackgroundResource(R.drawable.text_background);
                        if(option2.getText().toString().equals(questionClass.getCorrect_answer())){
                            score += 10;
                        }
                    } else if (selected.equals("C")) {
                        option3.setBackgroundResource(R.drawable.text_background);
                        if(option3.getText().toString().equals(questionClass.getCorrect_answer())){
                            score += 10;
                        }
                    } else if (selected.equals("D")) {
                        option4.setBackgroundResource(R.drawable.text_background);
                        if(option4.getText().toString().equals(questionClass.getCorrect_answer())){
                            score += 10;
                        }
                    }
                    selected = "";
                    if (questionNumber == 9) {
                        Intent resultsIntent = new Intent(MainActivity.this, ResultsActivity.class);
                        resultsIntent.putExtra("questionList", questionList);
                        resultsIntent.putExtra("score", score);
                        resultsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(resultsIntent);
                        finish();
                    }
                    if (questionNumber < 9) {
                        questionNumber++;
                        if (questionNumber == 9) {
                            next.setText("Submit and See Results");
                        }
                        generateQuestion(questionNumber);
                    }
                }
            }
        });

        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isSelected) {
                    isSelected = true;
                } else {
                    if (selected.equals("B")) {
                        option2.setBackgroundResource(R.drawable.text_background);
                    } else if (selected.equals("C")) {
                        option3.setBackgroundResource(R.drawable.text_background);
                    } else if (selected.equals("D")) {
                        option4.setBackgroundResource(R.drawable.text_background);
                    }
                }
                selected = "A";
                option1.setBackgroundResource(R.drawable.selected_answer);
            }
        });

        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isSelected) {
                    isSelected = true;
                } else {
                    if (selected.equals("A")) {
                        option1.setBackgroundResource(R.drawable.text_background);
                    } else if (selected.equals("C")) {
                        option3.setBackgroundResource(R.drawable.text_background);
                    } else if (selected.equals("D")) {
                        option4.setBackgroundResource(R.drawable.text_background);
                    }
                }
                selected = "B";
                option2.setBackgroundResource(R.drawable.selected_answer);
            }
        });

        option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isSelected) {
                    isSelected = true;
                } else {
                    if (selected.equals("B")) {
                        option2.setBackgroundResource(R.drawable.text_background);
                    } else if (selected.equals("A")) {
                        option1.setBackgroundResource(R.drawable.text_background);
                    } else if (selected.equals("D")) {
                        option4.setBackgroundResource(R.drawable.text_background);
                    }
                }
                selected = "C";
                option3.setBackgroundResource(R.drawable.selected_answer);
            }
        });

        option4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isSelected) {
                    isSelected = true;
                } else {
                    if (selected.equals("B")) {
                        option2.setBackgroundResource(R.drawable.text_background);
                    } else if (selected.equals("C")) {
                        option3.setBackgroundResource(R.drawable.text_background);
                    } else if (selected.equals("A")) {
                        option1.setBackgroundResource(R.drawable.text_background);
                    }
                }
                selected = "D";
                option4.setBackgroundResource(R.drawable.selected_answer);
            }
        });

    }

    public void generateQuestion(int questionNumber) {
        Question questionClass = questionList.get(questionNumber);
        question.setText(questionClass.getQuestion());
        String[] optionsClass = questionClass.getOptions();
        option1.setText(optionsClass[0]);
        option2.setText(optionsClass[1]);
        option3.setText(optionsClass[2]);
        option4.setText(optionsClass[3]);

    }

    public void getJSON(){
        String json;
        try {
            InputStream file = getAssets().open("questions.json");
            int size = file.available();
            byte[] buffer = new byte[size];
            file.read(buffer);
            file.close();
            json = new String(buffer, StandardCharsets.UTF_8);

            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("questions");

            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject questionObject = jsonArray.getJSONObject(i);
                Question questionClass = new Question();
                questionClass.setQuestion(Integer.toString(i+1) + ". " + questionObject.getString("question"));
                questionClass.setCorrect_answer(questionObject.getString("correct_answer"));
                JSONArray options = questionObject.getJSONArray("options");
                String[] optionsClass = new String[4];
                optionsClass[0] = options.getJSONObject(0).getString("A");
                optionsClass[1] = options.getJSONObject(1).getString("B");
                optionsClass[2] = options.getJSONObject(2).getString("C");
                optionsClass[3] = options.getJSONObject(3).getString("D");
                questionClass.setOptions(optionsClass);
                questionList.add(questionClass);
            }

        } catch (IOException e) {
            Toast.makeText(this, "IOE Error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (JSONException e) {
            Toast.makeText(this, "JSON Error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}