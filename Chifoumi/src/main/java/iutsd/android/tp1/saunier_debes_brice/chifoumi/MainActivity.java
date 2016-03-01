package iutsd.android.tp1.saunier_debes_brice.chifoumi;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity
    extends AppCompatActivity {

// ------------------------------ FIELDS ------------------------------

  ImagesAdapter adapter;
  int computerScore;
  int playerScore;

// -------------------------- OTHER METHODS --------------------------

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    RecyclerView cList = (RecyclerView) findViewById(R.id.cardsList);

    adapter = new ImagesAdapter(this);
    cList.setAdapter(adapter);

    //recyclerView.setLayoutManager(new LinearLayoutManager(this));
    GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
    cList.setLayoutManager(new GridLayoutManager(this, 2));

    cList.addOnItemTouchListener(new RecyclerItemClickListener(getBaseContext(),
        new RecyclerItemClickListener.OnItemClickListener() {
          @SuppressWarnings("Duplicates")
          @Override
          public void onItemClick(View view, int position) {
            int computerChoice = (int) (Math.random() * 4);

            final boolean computerHasSelectedPit      = computerChoice == 0;
            final boolean computerHasSelectedRock     = computerChoice == 1;
            final boolean computerHasSelectedScissors = computerChoice == 2;
            final boolean computerHasSelectedSheet    = computerChoice == 3;

            final boolean playerHasSelectedPit      = position == 0;
            final boolean playerHasSelectedRock     = position == 1;
            final boolean playerHasSelectedScissors = position == 2;
            final boolean playerHasSelectedSheet    = position == 3;

            if (playerHasSelectedPit) {
              if (computerHasSelectedRock || computerHasSelectedScissors)
                playerWin(computerChoice);
              else if (computerHasSelectedSheet)
                computerWin(computerChoice);
              else
                draw(computerChoice);
            }
            else if (playerHasSelectedRock){
              if (computerHasSelectedSheet || computerHasSelectedPit)
                computerWin(computerChoice);
              else if (computerHasSelectedScissors)
                playerWin(computerChoice);

              else
                draw(computerChoice);
            }
            else if (playerHasSelectedScissors){
              if (computerHasSelectedRock || computerHasSelectedPit)
                computerWin(computerChoice);
              else if (computerHasSelectedSheet)
                playerWin(computerChoice);
              else
                draw(computerChoice);
            }
            else if (playerHasSelectedSheet){
              if (computerHasSelectedScissors || computerHasSelectedPit)
                computerWin(computerChoice);
              else if (computerHasSelectedRock)
                playerWin(computerChoice);
              else
                draw(computerChoice);
            }
          }
        }));


   }

  private void draw(int computerChoice) {makeDrawAlert(computerChoice);}

  private void computerWin(int computerChoice) {
    computerScore++;
    setScoreText();
    makeComputerWinAlert(computerChoice);
  }

  private void setScoreText() {TextView scoreView = (TextView) findViewById(R.id.scores_msg);
    scoreView.setText(makeMessage());
  }

  private String makeMessage() {
    String msg = getBaseContext().getString(R.string.player_result);
    msg += " " + this.playerScore;
    msg += " " + getBaseContext().getString(R.string.computer_result);
    msg += " " + this.computerScore;
    return msg;
  }

  private void playerWin(int computerChoice) {
    playerScore++;
    setScoreText();
    makePlayerWinAlert(computerChoice);
  }

  private void makeDrawAlert(int computerChoice) {

  }

  private void makeComputerWinAlert(int computerChoice) {

  }

  private void makePlayerWinAlert(int computerChoice) {

  }


  public void onClickSnackbar(View v) {
    Snackbar.make(findViewById(android.R.id.content), "Replace with your own action", Snackbar.LENGTH_LONG)
        .setAction("Action", null).show();

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}
