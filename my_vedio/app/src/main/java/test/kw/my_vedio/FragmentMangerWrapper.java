package test.kw.my_vedio;


import android.support.v4.app.Fragment;

import java.util.HashMap;

public class FragmentMangerWrapper {
    private volatile  static FragmentMangerWrapper mangerWrapper = null;

    public static FragmentMangerWrapper getFragmentManger(){
        if (mangerWrapper==null){
            synchronized (FragmentMangerWrapper.class){
                if (mangerWrapper==null){
                    mangerWrapper = new FragmentMangerWrapper();
                }
            }
        }
        return mangerWrapper;
    }

    private HashMap<String,Fragment> hashMap = new HashMap<>();

    public Fragment createFragment(Class<?> clazz){
        return createFragment(clazz,true);
    }
    public Fragment createFragment(Class<?> clazz, boolean b){
        Fragment fragment = null;
        String className = clazz.getName();
        if(hashMap.containsKey(className)){
            fragment = (BaseFragment) hashMap.get(className);
        }else {
            try {
                fragment = (Fragment) Class.forName(className).newInstance();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (b){
            hashMap.put(className,fragment);
        }
        return fragment;
    }
}
