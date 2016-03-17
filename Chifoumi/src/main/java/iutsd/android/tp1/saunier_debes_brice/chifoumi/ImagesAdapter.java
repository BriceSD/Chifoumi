/*
 * ImagesAdapter.java
 * SAUNIER DEBES Brice
 * 29/02/16
 */

package iutsd.android.tp1.saunier_debes_brice.chifoumi;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;

/**
 * L’adapter d’images (cartes). Les converties en vues.
 */
public class ImagesAdapter
    extends RecyclerView.Adapter<ImagesAdapter.ImagesViewHolder>
    implements Serializable {

// ------------------------------ FIELDS ------------------------------

  /**
   * Le context de l’activité
   */
  private Context context;

  /**
   * Les noms des images.
   */
  private String[] imgNames;

// --------------------------- CONSTRUCTORS ---------------------------

  /**
   * Instancie un nouveau Images adapter.
   *
   * @param context le context
   */
  public ImagesAdapter(Context context) {
    this.context = context;
    this.imgNames = context.getResources().getStringArray(R.array.cards_name);
  }

// -------------------------- OTHER METHODS --------------------------

  /**
   * Get les ID des images.
   *
   * @return les id des images
   */
  //lolz
  //Comment faire en sorte de sauvegarder des ID des images ? Un typed array n’étant pas
  // sérialasable je n’ai pas trouvé une autre solution que de chercher ces informations dans la
  // classe R à chaque fois
  private TypedArray getImgIDs() {
    return context.getResources().obtainTypedArray(R.array.img_ids);
  }

  @Override
  public int getItemCount() {
    return imgNames.length;
  }

  @Override
  public void onBindViewHolder(ImagesViewHolder imagesViewHolder, int i) {
    //Lorsque la vue créée est liée à sa vue mère
    //L’image à l’index i est cherchée
    ImagesInfo img = getItem(i);

    //Le nom de l’image est mis dans la vue
    imagesViewHolder.vName.setText(img.name);
    //Puis l’image en elle même
    imagesViewHolder.vImage.setImageResource(img.id);

  }

  /**
   * Get les informations d’une image.
   *
   * @param i l’index de l’image
   *
   * @return Les informations des images
   */
  private ImagesInfo getItem(int i) {
    //Retourne les informations d’une image d’une manière compréhensible en java pour le coder
    return new ImagesInfo(getImgName(i), getImgIDs().getResourceId(i, -1));
  }

  /**
   * Get le nom d’une image.
   *
   * @param index l’index de l’image
   *
   * @return Le nom de l’image
   */
  public String getImgName(int index) {
    this.imgNames = context.getResources().getStringArray(R.array.cards_name);
    return imgNames[index];
  }

  @Override
  public ImagesViewHolder onCreateViewHolder(ViewGroup parent, int i) {
    // Crée une nouvelle vue en deserialisant du XML, ici le layout cards_view
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_view, parent, false);
    // Met la taille de la vue, les marges et les paramètres du layout
    ImagesViewHolder vh = new ImagesViewHolder(v);
    return vh;
  }

// -------------------------- INNER CLASSES --------------------------


  /**
   * Stock des données pour faciliter la liaison entre les contenus, permet entre autre de rentre le
   * scrolling plus "smooth", moins de lag
   */
  public class ImagesViewHolder
      extends RecyclerView.ViewHolder {
    /**
     * Le nom du TextView.
     */
    protected TextView  vName;
    /**
     * Le nom de l’ImageView.
     */
    protected ImageView vImage;

    /**
     * Instancie un nouveau ImagesViewHolder.
     *
     * @param v la vue
     */
    public ImagesViewHolder(View v) {
      super(v);
      vName = (TextView) v.findViewById(R.id.imgName);
      vImage = (ImageView) v.findViewById(R.id.imgContent);
    }
  }

}
