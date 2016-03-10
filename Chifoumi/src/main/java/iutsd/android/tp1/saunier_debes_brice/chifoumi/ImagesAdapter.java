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
 * The type Images adapter.
 */
public class ImagesAdapter
    extends RecyclerView.Adapter<ImagesAdapter.ImagesViewHolder> implements Serializable{

// ------------------------------ FIELDS ------------------------------

  /**
   * The Context.
   */
  private Context    context;

  /**
   * The Img names.
   */
  private String[]   imgNames;

// --------------------------- CONSTRUCTORS ---------------------------

  /**
   * Instantiates a new Images adapter.
   *
   * @param context the context
   */
  public ImagesAdapter(Context context) {
    this.context = context;
    this.imgNames = context.getResources().getStringArray(R.array.cards_name);
  }

// -------------------------- OTHER METHODS --------------------------

  //lolz
  private TypedArray getImgIDs() {
    return context.getResources().obtainTypedArray(R.array.img_ids);
  }

  public String getImgName(int index) {
    this.imgNames = context.getResources().getStringArray(R.array.cards_name);
    return imgNames[index];
  }
  @Override
  public int getItemCount() {
    return imgNames.length;
  }

  @Override
  public void onBindViewHolder(ImagesViewHolder imagesViewHolder, int i) {
    ImagesInfo img = getItem(i);

    imagesViewHolder.vName.setText(img.name);
    imagesViewHolder.vImage.setImageResource(img.id);

  }

  /**
   * Gets item.
   *
   * @param i the
   *
   * @return the item
   */
  private ImagesInfo getItem(int i) { return new ImagesInfo(getImgName(i), getImgIDs().getResourceId(i, -1));
  }

  @Override
  public ImagesViewHolder onCreateViewHolder(ViewGroup parent, int i) {
    // create a new view
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_view, parent, false);
    // set the view's size, margins, paddings and layout parameters
    ImagesViewHolder vh = new ImagesViewHolder(v);
    return vh;
  }

// -------------------------- INNER CLASSES --------------------------

  /**
   * The type Images view holder.
   */
  public class ImagesViewHolder
      extends RecyclerView.ViewHolder {
    /**
     * The V name.
     */
    protected TextView  vName;
    /**
     * The V image.
     */
    protected ImageView vImage;

    /**
     * Instantiates a new image view holder.
     *
     * @param v the v
     */
    public ImagesViewHolder(View v) {
      super(v);
      vName = (TextView) v.findViewById(R.id.imgName);
      vImage = (ImageView) v.findViewById(R.id.imgContent);
    }
  }

}
