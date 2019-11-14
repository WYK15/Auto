package com.ishumei.helper;

import android.accessibilityservice.AccessibilityService;
import android.os.Build;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.util.List;
import java.util.concurrent.Delayed;

public class EnvService extends AccessibilityService {


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

        int eventType = event.getEventType();
        //获取包名
        CharSequence packageName = event.getPackageName();
//        Toast.makeText(this,"name: "+packageName.toString(),Toast.LENGTH_SHORT).show();
        if (TextUtils.isEmpty(packageName)) {
            return;
        }


        //if (eventType == AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED) {
        AccessibilityNodeInfo nodeinfo = getRootInActiveWindow();
        if (nodeinfo == null) {
            //Toast.makeText(this,"111111111",Toast.LENGTH_SHORT).show();
            return;
        }

        performScollBack(nodeinfo);


//
//        List<AccessibilityNodeInfo> list = nodeinfo.findAccessibilityNodeInfosByText("BUTTON");
//
//        if (list.size() == 0) {
//         //   Toast.makeText(this, "没有找到相关节点", Toast.LENGTH_SHORT).show();
//        } else {
//         //   Toast.makeText(this, "找到相关节点"+ list.size(), Toast.LENGTH_SHORT).show();
//        }
//
//        for (int i = 0; i < list.size(); i++) {
//            AccessibilityNodeInfo thenode = list.get(i);
//            thenode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
//       //     Toast.makeText(this, "已经点击！"+list.size(), Toast.LENGTH_SHORT).show();
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }

    }

    public void performScollBack(AccessibilityNodeInfo nodeinfo) {
        boolean find = false;
        String ListViewClassname = "android.widget.ListView";
        String recycleClaname1 = "androidx.appcompat.app.AlertController.RecycleListView";
        String recycleClaname2 = "android.support.v7.widget.RecyclerView";


        for (int i = 0; i < nodeinfo.getChildCount(); i++) {

            AccessibilityNodeInfo FirstFloor = nodeinfo.getChild(i);
            int childlen = FirstFloor.getChildCount();
            if (childlen == 0) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String thenodename = (String) nodeinfo.getChild(i).getClassName();
                if (ListViewClassname.equals(thenodename) || recycleClaname1.equals(thenodename) ||
                        recycleClaname2.equals(thenodename)) {
                    Toast.makeText(this,"找到listview了！！",Toast.LENGTH_SHORT).show();
                    nodeinfo.getChild(i).performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);

                }

            } else {
                AccessibilityNodeInfo child = FirstFloor;
                //第二层
                for (int j = 0; j < childlen; j++) {
                    if (child.getChild(j) != null) {
                        String thenodename = (String) child.getChild(j).getClassName();
                        if (ListViewClassname.equals(thenodename) || recycleClaname1.equals(thenodename) ||
                                recycleClaname2.equals(thenodename)) {
                            Toast.makeText(this, "找到list了", Toast.LENGTH_SHORT).show();
                            child.getChild(j).performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);

                            try {
                                Thread.sleep(3000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            find = true;
                            break;
                        }
                    }
                }
                if (!find) Toast.makeText(this, "没有找到list", Toast.LENGTH_SHORT).show();
                if (find) break;
            }

        }
    }

    @Override
    public void onInterrupt() {

    }
}
