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
   * L’image adapter qui contient leurs noms, photos et méthodes pour les convertir en vues
   */
  ImagesAdapter adapter;
  /**
   * Le score de l’ordinateur
   */
  int           computerScore;
  /**
   * Le score du joueur
   */
  int           playerScore;
  /**
   * Liste compremant les différentes cartes (actions) à afficher
   */
  private RecyclerView cardsList;

// -------------------------- OTHER METHODS --------------------------

  /**
   * Fait le message de victoire de l’ordinateur.
   *
   * @param computerChoice l’index du choix de l’ordinateur
   *
   * @return La première partie du message de victoire de l’ordinateur
   */
  private String makeComputerWinMessage(int computerChoice) {
    String msg = makeComputerChoiceAlertMessage(computerChoice);
    msg += " " + getBaseContext().getString(R.string.computer_win_result_popup);
    return msg;
  }

  /**
   * Fait la première partie du message du snackbar. Soit le symbole sélectionné par l’ordinateur.
   *
   * @param computerChoice l’index du choix de l’ordinateur
   *
   * @return Le message de victoire de l’ordinateur
   */
  private String makeComputerChoiceAlertMessage(int computerChoice) {
    String msg = getBaseContext().getString(R.string.computer);
    msg += " " + adapter.getImgName(computerChoice);
    return msg;
  }

  /**
   * Fait le message de match nul.
   *
   * @param computerChoice l’index du choix de l’ordinateur
   *
   * @return Le message de match nul
   */
  private String makeDrawMessage(int computerChoice) {
    String msg = makeComputerChoiceAlertMessage(computerChoice);
    msg += " " + getBaseContext().getString(R.string.draw_result_popup);
    return msg;
  }

  /**
   * Fait le message de victoire du joueur.
   *
   * @param computerChoice l’index du choix de l’ordinateur
   *
   * @return La première partie du message de victoire du joueur
   */
  private String makePlayerWinMessage(int computerChoice) {
    String msg = makeComputerChoiceAlertMessage(computerChoice);
    msg += " " + getBaseContext().getString(R.string.player_win_result_popup);
    return msg;
  }


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    //En premier les cardsView sont crées, puis le bon layout est choisi en fonction de
    // l’orientation de l’écran, les cartes sont attachés à l’activité mère, ajout des listener,
    // supprime le récapitulatif des scores si necessaire (comme en cas d’écran tourné sans action préalable)
    setUpCardsListView();
    setUpLayoutManager();
    setOnItemTouchListener();
    eraseScoreMessageIfNeeded();
  }

  /**
   * Crée la vue contenant les différentes cartes.
   */
  private void setUpCardsListView() {
    adapter = new ImagesAdapter(this);

    RecyclerView cardsList = (RecyclerView) findViewById(R.id.cards_list);
    cardsList.setAdapter(adapter);
    this.cardsList = cardsList;
  }

  /**
   * Crée le layout manager selon l’orientation de l’écran.
   */
  private void setUpLayoutManager() {
    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
      cardsList.setLayoutManager(new GridLayoutManager(this, 2));
    else
      cardsList
          .setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
  }

  /**
   * Mets en place le « on item click listener ». Permet à l’utilisateur de cliquer sur les images.
   * L’ordinateur fait son choix d’action and en fonction du vaiqueur du match, le nouveau score
   * est calculé et le nouveau message est affiché
   */
  private void setOnItemTouchListener() {
    cardsList.addOnItemTouchListener(new RecyclerItemClickListener(getBaseContext(),
        new RecyclerItemClickListener.OnItemClickListener() {
          @SuppressWarnings("Duplicates")
          @Override
          public void onItemClick(View view, int playerChoice) {
            int computerChoice = (int) (Math.random() * 4);

            final boolean computerHasSelectedPit      = computerChoice == 0;
            final boolean computerHasSelectedRock     = computerChoice == 1;
            final boolean computerHasSelectedScissors = computerChoice == 2;
            final boolean computerHasSelectedSheet    = computerChoice == 3;

            final boolean playerHasSelectedPit      = playerChoice == 0;
            final boolean playerHasSelectedRock     = playerChoice == 1;
            final boolean playerHasSelectedScissors = playerChoice == 2;
            final boolean playerHasSelectedSheet    = playerChoice == 3;

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
   * Les actions gagnantent de l’ordinateur.
   * Augmente son score, met à jours le message récapitulant les scores et affiche le snackbar.
   *
   * @param computerChoice l’index du choix de l’ordinateur
   */
  private void computerWin(int computerChoice) {
    computerScore++;
    setScoreText();
    makeComputerWinAlert(computerChoice);
  }

  /**
   * Met le score dans le récapitulatif des scores
   */
  private void setScoreText() {
    TextView scoreView = (TextView) findViewById(R.id.scores_msg);
    scoreView.setText(makeScoreMessage());
  }

  /**
   * Fait le message de récapitulatif des scores
   *
   * @return le message contenant les scores
   */
  private String makeScoreMessage() {
    String msg = getBaseContext().getString(R.string.player_result);
    msg += " " + playerScore;
    msg += " " + getBaseContext().getString(R.string.computer_result);
    msg += " " + computerScore;
    return msg;
  }

  /**
   * Affiche le snackbar de victoire de l’ordinateur
   *
   * @param computerChoice l’index du choix de l’ordinateur
   */
  private void makeComputerWinAlert(int computerChoice) {
    //OUUUUUUUUUUUIIIIIIIIIIIIIIII J’AI RÉUSSI J’AI UN SNACKBAR
    Snackbar.make(findViewById(android.R.id.content), makeComputerWinMessage(computerChoice),
        Snackbar.LENGTH_LONG).setAction("Action", null).show();
  }

  /**
   * Actions en cas de victoire du joueur.
   * Augmente son score, met à jours le message récapitulant les scores et affiche le snackbar.
   *
   * @param computerChoice l’index du choix de l’ordinateur
   */
  private void playerWin(int computerChoice) {
    playerScore++;
    setScoreText();
    makePlayerWinAlert(computerChoice);
  }

  /**
   * Affiche le snackbar de victoire du joueur.
   *
   * @param computerChoice l’index du choix de l’ordinateur
   */
  private void makePlayerWinAlert(int computerChoice) {
    Snackbar.make(findViewById(android.R.id.content), makePlayerWinMessage(computerChoice),
        Snackbar.LENGTH_LONG).setAction("Action", null).show();
  }

  /**
   * Actions en cas de match nul.
   *
   * @param computerChoice l’index du choix de l’ordinateur
   */
  private void draw(int computerChoice) {
    makeDrawAlert(computerChoice);
  }

  /**
   * Affiche le snackbar de match nul
   *
   * @param computerChoice l’index du choix de l’ordinateur
   */
  private void makeDrawAlert(int computerChoice) {
    Snackbar.make(findViewById(android.R.id.content), makeDrawMessage(computerChoice),
        Snackbar.LENGTH_LONG).setAction("Action", null).show();
  }

  /**
   * Efface le récapitulatif des scores si necessaire
   */
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

  /**
   * Réinitialise les scores et le récapitulatif des scores lors du click sur le bouton.
   *
   * @param v la vue
   */
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

    //Comme d’hab, les variables sauvegardées sont réutilisées
    this.adapter = (ImagesAdapter) savedInstanceState.getSerializable("ImagesAdapter");
    this.playerScore = savedInstanceState.getInt("PlayerScore");
    this.computerScore = savedInstanceState.getInt("ComputerScore");

    TextView scoreMsg = (TextView) findViewById(R.id.scores_msg);
    scoreMsg.setText(makeScoreMessage());
  }

  @Override
  protected void onResume() {
    super.onResume();

    //Suppression du message des scores si necessaire
    eraseScoreMessageIfNeeded();
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);

    //Comme d’hab, les variables contenant des informations importantes sont sauvegardées
    outState.putSerializable("ImagesAdapter", this.adapter);
    outState.putInt("PlayerScore", this.playerScore);
    outState.putInt("ComputerScore", this.computerScore);
  }
}
