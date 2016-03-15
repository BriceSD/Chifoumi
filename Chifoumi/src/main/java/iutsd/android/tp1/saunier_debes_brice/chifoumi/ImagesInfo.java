/*
 * ImagesInfo.java
 * SAUNIER DEBES Brice
 * 29/02/16
 */

package iutsd.android.tp1.saunier_debes_brice.chifoumi;

/**
 * Cette class contient les différentes information concernant une image.
 */
public class ImagesInfo {

// ------------------------------ FIELDS ------------------------------

  /**
   * Le nom de l’image
   */
  protected String name;
  /**
   * L’ID de l’image
   */
  protected int id;

// --------------------------- CONSTRUCTORS ---------------------------

  /**
   * Instancie un nouveau ImageInfo
   *
   * @param name Le nom permetant d’identifier l’image
   * @param id   L’id permetant d’identifier l’image
   */
  public ImagesInfo(String name, int id){
    this.name = name;
    this.id = id;
  }
}
