package iutsd.android.tp1.saunier_debes_brice.chifoumi;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.Serializable;

/**
 * The type Main activity.
 */
public class MainActivity
    extends AppCompatActivity
    implements Serializable {

// ------------------------------ FIELDS ------------------------------

  /**
   * The Adapter.
   */
  ImagesAdapter adapter;
  /**
   * The Computer score.
   */
  int           computerScore;
  /**
   * The Player score.
   */
  int           playerScore;

// -------------------------- OTHER METHODS --------------------------

  private String makeComputerWinMessage(int computerChoice) {
    String msg = makeComputerChoiceAlertMessage(computerChoice);
    msg += " " + getBaseContext().getString(R.string.computer_win_result_popup);
    return msg;
  }

  private String makeComputerChoiceAlertMessage(int computerChoice) {
    String msg = getBaseContext().getString(R.string.computer);
    msg += " " + adapter.getImgName(computerChoice);
    return msg;
  }

  private String makeDrawMessage(int computerChoice) {
    String msg = makeComputerChoiceAlertMessage(computerChoice);
    msg += " " + getBaseContext().getString(R.string.draw_result_popup);
    return msg;
  }

  private String makePlayerWinMessage(int computerChoice) {
    String msg = makeComputerChoiceAlertMessage(computerChoice);
    msg += " " + getBaseContext().getString(R.string.player_win_result_popup);
    return msg;
  }


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    RecyclerView cardsList;

    cardsList = setUpCardsListView();
    setUpLayoutManager(cardsList);
    setOnItemTouchListener(cardsList);
    eraseScoreMessageIfNeeded();
  }

  private RecyclerView setUpCardsListView() {
    adapter = new ImagesAdapter(this);

    RecyclerView cardsList = (RecyclerView) findViewById(R.id.cards_list);
    cardsList.setAdapter(adapter);
    return cardsList;
  }

  private void setUpLayoutManager(RecyclerView cardsList) {
    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
      cardsList.setLayoutManager(new GridLayoutManager(this, 2));
    else
      cardsList
          .setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
  }

  private void setOnItemTouchListener(RecyclerView cardsList) {
    cardsList.addOnItemTouchListener(new RecyclerItemClickListener(getBaseContext(),
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
            } else if (playerHasSelectedSheet) {
              if (computerHasSelectedRock || computerHasSelectedPit)
                playerWin(computerChoice);
              else if (computerHasSelectedScissors)
                computerWin(computerChoice);
              else
                draw(computerChoice);
            } else if (playerHasSelectedRock) {
              if (computerHasSelectedSheet || computerHasSelectedPit)
                computerWin(computerChoice);
              else if (computerHasSelectedScissors)
                playerWin(computerChoice);
              else
                draw(computerChoice);
            } else if (playerHasSelectedScissors) {
              if (computerHasSelectedRock || computerHasSelectedPit)
                computerWin(computerChoice);
              else if (computerHasSelectedSheet)
                playerWin(computerChoice);
              else
                draw(computerChoice);
            }
          }
        }));
  }

  /**
   * Computer win.
   *
   * @param computerChoice the computer choice
   */
  private void computerWin(int computerChoice) {
    computerScore++;
    setScoreText();
    makeComputerWinAlert(computerChoice);
  }

  /**
   * Sets score text.
   */
  private void setScoreText() {
    TextView scoreView = (TextView) findViewById(R.id.scores_msg);
    scoreView.setText(makeScoreMessage());
  }

  /**
   * Make the score message string.
   *
   * @return the score message.
   */
  private String makeScoreMessage() {
    String msg = getBaseContext().getString(R.string.player_result);
    msg += " " + playerScore;
    msg += " " + getBaseContext().getString(R.string.computer_result);
    msg += " " + computerScore;
    return msg;
  }

  /**
   * Make computer win alert.
   *
   * @param computerChoice the computer choice
   */
  private void makeComputerWinAlert(int computerChoice) {
    Snackbar.make(findViewById(android.R.id.content), makeComputerWinMessage(computerChoice),
        Snackbar.LENGTH_LONG).setAction("Action", null).show();
  }

  /**
   * Player win.
   *
   * @param computerChoice the computer choice
   */
  private void playerWin(int computerChoice) {
    playerScore++;
    setScoreText();
    makePlayerWinAlert(computerChoice);
  }

  /**
   * Make player win alert.
   *
   * @param computerChoice the computer choice
   */
  private void makePlayerWinAlert(int computerChoice) {
    Snackbar.make(findViewById(android.R.id.content), makePlayerWinMessage(computerChoice),
        Snackbar.LENGTH_LONG).setAction("Action", null).show();
  }

  /**
   * Draw.
   *
   * @param computerChoice the computer choice
   */
  private void draw(int computerChoice) {
    makeDrawAlert(computerChoice);
  }

  /**
   * Make draw alert.
   *
   * @param computerChoice the computer choice
   */
  private void makeDrawAlert(int computerChoice) {
    Snackbar.make(findViewById(android.R.id.content), makeDrawMessage(computerChoice),
        Snackbar.LENGTH_LONG).setAction("Action", null).show();
  }

  private void eraseScoreMessageIfNeeded() {
    TextView scoreMsg = (TextView) findViewById(R.id.scores_msg);
    if (playerScore <= 0 && computerScore <= 0)
      scoreMsg.setText("");
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  public void onEraseButtonClick(View v) {
    this.computerScore = 0;
    this.playerScore = 0;
    eraseScoreMessageIfNeeded();
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

  @Override
  protected void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);

    this.adapter = (ImagesAdapter) savedInstanceState.getSerializable("ImagesAdapter");
    this.playerScore = savedInstanceState.getInt("PlayerScore");
    this.computerScore = savedInstanceState.getInt("ComputerScore");

    TextView scoreMsg = (TextView) findViewById(R.id.scores_msg);
    scoreMsg.setText(makeScoreMessage());
  }

  @Override
  protected void onResume() {
    super.onResume();
    eraseScoreMessageIfNeeded();
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);

    outState.putSerializable("ImagesAdapter", this.adapter);
    outState.putInt("PlayerScore", this.playerScore);
    outState.putInt("ComputerScore", this.computerScore);
  }
}
