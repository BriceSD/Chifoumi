/*
 * ImagesViewHolder.java
 * SAUNIER DEBES Brice
 * 29/02/16
 */

package iutsd.android.tp1.saunier_debes_brice.chifoumi;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ImagesViewHolder extends RecyclerView.ViewHolder {

// ------------------------------ FIELDS ------------------------------

  protected TextView vName;
  protected ImageView vImage;

// --------------------------- CONSTRUCTORS ---------------------------

  public ImagesViewHolder(View v){
    super(v);
    vName = (TextView) v.findViewById(R.id.imgName);
    vImage = (ImageView) v.findViewById(R.id.imgContent);
  }
}
