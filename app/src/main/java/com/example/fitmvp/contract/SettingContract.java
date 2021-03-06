package com.example.fitmvp.contract;

public interface SettingContract {
    interface Model {
        void updateInfo(String tel, String nickname, String birthday,
                        String gender, String height, String weight, String cal, String pro, String fat, String ch2o, final InfoHint infoHint);
        interface InfoHint {
            void successInfo();
            void failInfo();
        }
    }

    interface View {
    }

    interface Presenter {
        void updateInfo(String tel, String oldNickname, String nickname, String birthday,
                        String gender, String height, String weight, String cal, String pro, String fat, String ch2o);
    }
}
