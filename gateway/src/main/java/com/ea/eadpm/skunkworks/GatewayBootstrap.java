/*
 Copyright (C) 2016 Electronic Arts Inc.  All rights reserved.
 */

package com.ea.eadpm.skunkworks;

import com.ea.async.Async;

import cloud.orbit.container.Container;

import javax.inject.Singleton;

/**
 * Created by jhegarty@ea.com on 2016-05-25.
 */

@Singleton
public class GatewayBootstrap
{
    static
    {
        Async.init();
    }

    public static void main(String [] args)
    {
        final Container container = new Container();
        container.start().join();
    }
}
