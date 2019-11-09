package com.weberson.corredor.Adaptadores;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.weberson.corredor.Fragmento.CentroFragment;
import com.weberson.corredor.Fragmento.DireitoFragment;
import com.weberson.corredor.Fragmento.EquerdoFragment;

public class adpitadorFraguimentos extends FragmentStatePagerAdapter {

String [] tbAr = new String[]{"Esquerda", "Centro", "Direito"};

Integer integer = 3;

    public adpitadorFraguimentos(FragmentManager fm) {
        super(fm);
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {


        return tbAr [position];
    }

    @Override
    public Fragment getItem(int i) {



        switch (i)
        {
            case 0:
                EquerdoFragment esquerdo = new EquerdoFragment();
                return esquerdo;
            case 1:
                 CentroFragment centro =new CentroFragment();
                 return centro;

            case 2: DireitoFragment direito = new DireitoFragment();
            return direito;




        }


        return null;
    }






    @Override
    public int getCount() {
        return integer;
    }
}
