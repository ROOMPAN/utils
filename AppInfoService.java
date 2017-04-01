package com.leshu.leshuvoice.__widgets;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import com.leshu.leshuvoice.__models.FloatAppInfoBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liupanpan on 2017/3/31.
 */

public class AppInfoService {
    private Context context;
    private PackageManager pm;

    public AppInfoService(Context context) {
        // TODO Auto-generated constructor stub
        this.context = context;
        pm = context.getPackageManager();
    }

    /**
     * 得到手机中所有的应用程序信息
     *
     * @return
     */
    public List<FloatAppInfoBean> getAppInfos() {
        //创建要返回的集合对象
        List<FloatAppInfoBean> appInfos = new ArrayList<FloatAppInfoBean>();
        //获取手机中所有安装的应用集合
        List<ApplicationInfo> applicationInfos = pm.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
        //遍历所有的应用集合
        for (ApplicationInfo info : applicationInfos) {
            //过滤掉系统app
            if (!filterApp(info)) {
                continue;
            }
            FloatAppInfoBean appInfo = new FloatAppInfoBean();
            //获取应用程序的图标
            Drawable app_icon = info.loadIcon(pm);
            appInfo.setImage(app_icon);
            //获取应用的名称
            String app_name = info.loadLabel(pm).toString();
            appInfo.setAppName(app_name);
            //获取应用的包名
            String packageName = info.packageName;
            appInfo.setPackageName(packageName);
            try {
                //获取应用的版本号
                PackageInfo packageInfo = pm.getPackageInfo(packageName, 0);
                String app_version = packageInfo.versionName;
                //appInfo.setApp_version(app_version);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            //判断应用程序是否是用户程序
            boolean isUserApp = filterApp(info);
            // appInfo.setUserApp(isUserApp);
            appInfos.add(appInfo);
        }
        return appInfos;
    }

    //判断应用程序是否是用户程序
    public boolean filterApp(ApplicationInfo info) {
        //原来是系统应用，用户手动升级
        if ((info.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
            return false;
            //用户自己安装的应用程序
        } else if ((info.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
            return true;
        }
        return false;
    }
}
