package at.bwappsandmore.doitagain.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import at.bwappsandmore.doitagain.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.recyclerview_item.view.*
import at.bwappsandmore.doitagain.room.DoItAgainEntity

class ActivitiesAdapter(val listener : (DoItAgainEntity, Int) -> Unit) : RecyclerView.Adapter<ActivitiesAdapter.ActivitiesViewHolder>() {

    private var activities = emptyList<DoItAgainEntity>()

    internal fun setActivities(activities: List<DoItAgainEntity>) {
        this.activities = activities
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivitiesViewHolder =
        ActivitiesViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.recyclerview_item,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ActivitiesViewHolder, position: Int) {
        holder.bind(activities[position])
    }

    override fun getItemCount() = activities.size

    inner class ActivitiesViewHolder(
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        // you can set an integer for each action
        init {
            itemView.resetIB.setOnClickListener {
                listener(activities[adapterPosition], 0)
                Log.d(null, "btn reset pressed")
            }
            itemView.editIB.setOnClickListener {
                listener(activities[adapterPosition], 1)
                Log.d(null, "btn edit pressed")
            }
        }

        fun bind(vo: DoItAgainEntity) {
            itemView.activity_title.text = vo.engagement
            itemView.sinceDaysTV.text = vo.daysSinceCounter.toString()
        }
    }
}

