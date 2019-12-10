package com.broada.uyconf.client.addons.properties;

/**
 * property reload listener
 */
public interface IReloadablePropertiesListener {

    void propertiesReloaded(PropertiesReloadedEvent event);
}
