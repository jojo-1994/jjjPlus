package com.sz.jjj.dagger;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Scope;

import dagger.releasablereferences.CanReleaseReferences;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by jjj on 2018/2/24.
 *
 * @description:
 */

@Documented
@Retention(RUNTIME)
@CanReleaseReferences
@Scope
public @interface ActivityScope {}