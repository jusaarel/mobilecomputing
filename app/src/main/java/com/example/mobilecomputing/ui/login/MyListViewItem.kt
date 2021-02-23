import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import com.example.mobilecomputing.R

class MyListViewItem(private val context: Context, private val arrayList: java.util.ArrayList<String>) : BaseAdapter() {
    private lateinit var name: TextView
    private lateinit var button : Button

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

        button.setOnClickListener {
            arrayList.removeAt(position)
            notifyDataSetChanged();

        }

        convertView.setOnClickListener {

        }

        button.text = "remove"
        name.text = arrayList[position]
        return convertView
    }
}