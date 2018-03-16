package com.sz.jjj.dagger;

import dagger.BindsInstance;
import dagger.Component;

/**
 * Created by jjj on 2018/2/24.
 *
 * @description:
 */

@MyScope
@Component
public interface ActivityComponent {

    ActivityModule getActivityModule();

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder setName(String name);

        ActivityComponent build();
    }
}
