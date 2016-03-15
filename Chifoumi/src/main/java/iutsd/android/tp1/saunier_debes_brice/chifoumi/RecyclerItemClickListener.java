package iutsd.android.tp1.saunier_debes_brice.chifoumi;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Utiliser cette classe pour gérer le simple clic (touché-relaché) via un OnItemTouchListener (utilisé avec un RecyclerView)
 */
public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
    private OnItemClickListener mListener;

    /**
     * L'interface que l'appelant devra implémenter pour notifier du clic
     */
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private GestureDetector mGestureDetector;

    /**
     * Constructeur
     * @param context : le context de l'application
     * @param listener : l'écouteur du clic
     */
    public RecyclerItemClickListener(Context context, OnItemClickListener listener) {
        mListener = listener;
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }

    /**
     * Gestion du Touch pour l'utiliser comme un clic (conversion du touch event en un évènement clic)
     * Ce n'est pas vous qui appelez cette méthode, c'est le RecyclerView lorsqu'un touch est réalisé.
     * @param view : le recyclerView
     * @param e : le type d'event touch
     * @return false si vous voulez arrêter la propagation de l'event (le cas ici)
     */
    @Override
    public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
        View childView = view.findChildViewUnder(e.getX(), e.getY());
        if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
            mListener.onItemClick(childView, view.getChildAdapterPosition(childView));
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
