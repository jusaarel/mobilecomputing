import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.room.Room
import com.example.mobilecomputing.EditReminder
import com.example.mobilecomputing.R
import com.example.mobilecomputing.ui.login.*
import kotlinx.coroutines.Dispatchers.Main


class MyListViewItem(private val context: Context, private val arrayList: java.util.ArrayList<MainActivity.ReminderListItem>) : BaseAdapter() {
    private lateinit var name: TextView
    private lateinit var button : Button
    private lateinit var image : ImageView

    override fun getCount(): Int {
        return arrayList.size
    }
    override fun getItem(position: Int): Any {
        return position
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        var convertView = convertView
        convertView = LayoutInflater.from(context).inflate(R.layout.listview_withbutton, parent, false)

        name = convertView.findViewById(R.id.tvItem)
        button = convertView.findViewById(R.id.button);
        image = convertView.findViewById(R.id.imageIcon);

        image.setImageResource(arrayList[position].resId)

        button.setOnClickListener {
            val item = arrayList[position]
            arrayList.removeAt(position)

            AppDatabase.delete(item.name)
            notifyDataSetChanged();

        }

        name.setOnClickListener {
            val item = arrayList[position]
            val intent = Intent(this.context, EditReminder::class.java)
            intent.putExtra("Name", item.name)
            ContextCompat.startActivity(this.context, intent, null)
        }

        button.text = "remove"
        name.text = arrayList[position].name
        return convertView
    }

    override fun notifyDataSetChanged() {

        super.notifyDataSetChanged()

    }
}