/*
 Copyright (C) 2016 Electronic Arts Inc.  All rights reserved.
 */

package com.ea.eadpm.skunkworks;

import cloud.orbit.container.Container;


/**
 * Created by jhegarty@ea.com on 2016-05-26.
 */
public class SingleEnvironmentLauncher
{
    public static void main(String [] args)
    {
        final Container container = new Container();

        container.start().join();
    }

}
