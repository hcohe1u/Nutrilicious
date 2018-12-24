package com.exemplys.nutrilicious.view.main

import android.support.v7.widget.RecyclerView
import android.view.*
import com.exemplys.nutrilicious.R
import com.exemplys.nutrilicious.model.Food
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.rv_item.*  // Imports synthetic properties


class SearchListAdapter(
    private var items: List<Food>  // Uses a read-only list of items to display
) : RecyclerView.Adapter<SearchListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)  // Inflates layout to create view
            .inflate(R.layout.rv_item, parent, false)
        return ViewHolder(view)  // Creates view holder that manages the list item view
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindTo(items[position])  // Binds data to a list item
    }

    // In this app, we'll usually replace all items so DiffUtil has little use

    fun setItems(newItems: List<Food>) {
        this.items = newItems   // Replaces whole list
        notifyDataSetChanged()  // Notifies recycler view of data changes to re-render
    }

    inner class ViewHolder(
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {


        fun bindTo(food: Food) {  // Populates text views and star image to show a food
            tvFoodName.text = food.name
            tvFoodType.text = food.type

            val image = if (food.isFavorite) {
                android.R.drawable.btn_star_big_on
            } else {
                android.R.drawable.btn_star_big_off
            }
            ivStar.setImageResource(image)
        }
    }
}