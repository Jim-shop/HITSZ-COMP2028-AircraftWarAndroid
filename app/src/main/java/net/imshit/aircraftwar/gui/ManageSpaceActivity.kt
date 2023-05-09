package net.imshit.aircraftwar.gui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import net.imshit.aircraftwar.databinding.ActivityManageSpaceBinding

class ManageSpaceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(ActivityManageSpaceBinding.inflate(layoutInflater)) {
            setContentView(root)
            // TODO 有了存储逻辑之后记得加入
        }
    }
}