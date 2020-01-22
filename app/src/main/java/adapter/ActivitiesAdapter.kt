package adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bwappsandmore.doitagain.R
import room.DoItAgainEntity

class ActivitiesAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<ActivitiesAdapter.ActivitiesViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var activities = emptyList<DoItAgainEntity>()
    val mContext = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivitiesViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return ActivitiesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ActivitiesViewHolder, position: Int) {
        val current = activities[position]
        holder.activityItemView.text = current.engagement
    }

    internal fun setActivities(activities: List<DoItAgainEntity>) {
        this.activities = activities
        notifyDataSetChanged()
    }

    override fun getItemCount() = activities.size

    inner class ActivitiesViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val activityItemView: TextView = itemview.findViewById(R.id.textView)
    }
}