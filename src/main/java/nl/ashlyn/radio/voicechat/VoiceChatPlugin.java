package nl.ashlyn.radio.voicechat;

import de.maxhenkel.voicechat.api.Group;
import de.maxhenkel.voicechat.api.VoicechatApi;
import de.maxhenkel.voicechat.api.VoicechatPlugin;
import de.maxhenkel.voicechat.api.VoicechatServerApi;
import de.maxhenkel.voicechat.api.events.CreateGroupEvent;
import de.maxhenkel.voicechat.api.events.EventRegistration;
import de.maxhenkel.voicechat.api.events.VoicechatServerStartedEvent;
import nl.ashlyn.core.utils.Logger;
import nl.ashlyn.radio.Radio;

import java.util.Arrays;

/**
 * Created by Ashlyn on 05/12/2025
 */
public class VoiceChatPlugin implements VoicechatPlugin {

    private static VoicechatServerApi api;

    private static final String[] CHANNELS = {
            "5.44 MHz",
            "7.52 MHz",
            "9.73 MHz",
            "12.15 MHz",
            "17.20 MHz"
    };

    @Override
    public String getPluginId() {
        return Radio.PLUGIN_ID;
    }

    @Override
    public void initialize(VoicechatApi api) {
        VoiceChatPlugin.api = (VoicechatServerApi) api;
    }

    @Override
    public void registerEvents(EventRegistration registration) {
        registration.registerEvent(VoicechatServerStartedEvent.class, this::onServerStarted, 100);
        registration.registerEvent(CreateGroupEvent.class, this::onGroupCreated, 100);
    }

    public void onServerStarted(VoicechatServerStartedEvent event) {
        VoiceChatPlugin.api = event.getVoicechat();

        for (String name : CHANNELS) {
            Group group = api.groupBuilder()
                    .setPersistent(true)
                    .setName(name)
                    .setType(Group.Type.NORMAL)
                    .build();

            Logger.logRadio("Created voicechat group: " + group.getName());
        }
    }

    public void onGroupCreated(CreateGroupEvent event) {
        String groupName = event.getGroup().getName();

        if (!Arrays.asList(CHANNELS).contains(groupName)) {
            event.cancel();
            Logger.logRadio("Cancelled creation of unauthorized group: " + groupName);
        }
    }
}
