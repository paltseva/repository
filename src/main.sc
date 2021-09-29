require: slotfilling/slotFilling.sc
  module = sys.zb-common
  
# Подключаем скриптовую обвязку
require: onboarding.js
require: main.js

init:
    script:
        initPreProcess();
# from main
theme: /
    state: smartHomeOnboarding
        intent!: /Onboardingв
        script:
            action_handler(
                toOnboardingString($parseTree._question.type),
                $context, 
                $parseTree, 
                $response
            );
    #state: smartHomeOnboarding
    #    intent!: /Onboarding
    #    script:
    #        onboarding_multi_reply(
    #            $context,
    #            $response,
    #            $parseTree._question.type
    #        );hjhjh
    state: smartHomeBuyDevice
        intent!: /Onboarding/BuyDevice
        script:
            action_handler(
                "ONBOARDING_BUY_DEVICE", 
                $context, 
                $parseTree, 
                $response
            );

#    state: smartHomeOnboarding_DEFAULT_CONTACTINFO
#        event!: HELP_WITH_CONTACT_INFO
#        event!: HELP_DEFAULT
#        script:
#            $request.data.eventData.image = "https://sberdevices2.s3pd01.sbercloud.ru/smartmarket-smide-prod/447/448/snSy6QX0f2wel1tl.png";
#            $request.data.eventData.image_hash = "9dcf38cf4168ea68bb6f00b2486b3838";
#            sendHelp($request.data.eventData, $response);
            
    state: smartHomeOnboarding_INFO
        event!: HELP_INFO_SERVER_ACTION
        script:
            action_handler(
                "HELP_INFO_SERVER_ACTION", 
                $context, 
                $parseTree, 
                $response
            );
            
    state:
        intent!: /ApplicationCall
        intent!: /ApplicationCall/StartSmarthome
        script:
            action_handler_for_logging('START_SMART_HOME', $context);
            openSmartHomeDeeplink($context, $response);

    state: smartHomeControlLampScene
        intent!: /Home/Lamp_Scene
        script:
            action_handler(
                "HOME_CONTROL_LAMP_SCENE", 
                $context, 
                $parseTree, 
                $response
            );
            
    state: smartHomeControlDeviceStatus
        intent!: /Home/Device_Status
        script:
            action_handler(
                "HOME_CONTROL_DEVICE_STATUS", 
                $context, 
                $parseTree, 
                $response
            );

    state: smartHomeControllLightOn
        intent!: /Home/Turn_On
        script:
            action_handler(
                "HOME_CONTROLL_LIGHT_ON", 
                $context, 
                $parseTree, 
                $response
            );

    state: smartHomeControllLightOff
        intent!: /Home/Turn_Off
        script:
            action_handler(
                "HOME_CONTROLL_LIGHT_OFF", 
                $context, 
                $parseTree, 
                $response
            );

    state: smartHomeControllLightBrightness
        intent!: /Home/Brightness
        script:
            action_handler(
                "HOME_CONTROLL_LIGHT_BRIGHTNESS", 
                $context, 
                $parseTree, 
                $response
            );

    state: smartHomeControllLightBrightnessLess
        intent!: /Home/Brightness_Less
        script:
            action_handler(
                "HOME_CONTROLL_LIGHT_BRIGHTNESS_LESS", 
                $context, 
                $parseTree, 
                $response
            );

    state: smartHomeControllLightBrightnessMore
        intent!: /Home/Brightness_More
        script:
            action_handler(
                "HOME_CONTROLL_LIGHT_BRIGHTNESS_MORE", 
                $context, 
                $parseTree, 
                $response
            );

    state: smartHomeControllLightColor
        intent!: /Home/Color
        script:
            action_handler(
                "HOME_CONTROLL_LIGHT_COLOR", 
                $context, 
                $parseTree, 
                $response
            );

    state: smartHomeControlColorTemp
        intent!: /Home/ColorTemp
        script:
            action_handler(
                "HOME_CONTROL_COLOR_TEMP", 
                $context, 
                $parseTree, 
                $response
            );
            
    state: smartHomeControlLock
        intent!: /Home/Lock
        script:
            action_handler(
                "HOME_CONTROL_CHILD_LOCK", 
                $context, 
                $parseTree, 
                $response
            );
            
    state: smartHomeControlUnlock
        intent!: /Home/Unlock
        script:
            action_handler(
                "HOME_CONTROL_CHILD_UNLOCK", 
                $context, 
                $parseTree, 
                $response
            );
            
    state: smartHomeControlTVMute
        intent!: /TV/Mute
        script:
            action_handler(
                "HOME_CONTROL_MUTE", 
                $context, 
                $parseTree, 
                $response
            );
            
    state: smartHomeControlTVUnmute
        intent!: /TV/Unmute
        script:
            action_handler(
                "HOME_CONTROL_UNMUTE", 
                $context, 
                $parseTree, 
                $response
            );
            
    state: smartHomeControlTVVolumeMore
        intent!: /TV/Volume_More
        script:
            action_handler(
                "HOME_CONTROL_VOLUME_MORE", 
                $context, 
                $parseTree, 
                $response
            );
            
    state: smartHomeControlTVVolumeLess
        intent!: /TV/Volume_Less
        script:
            action_handler(
                "HOME_CONTROL_VOLUME_LESS", 
                $context, 
                $parseTree, 
                $response
            );
            
    state: smartHomeControlTVVolume
        intent!: /TV/Volume
        script:
            action_handler(
                "HOME_CONTROL_VOLUME", 
                $context, 
                $parseTree, 
                $response
            );
            
    state: smartHomeControlTVVolumeInfo
        intent!: /TV/Volume_Info
        script:
            action_handler(
                "HOME_CONTROL_VOLUME_INFO", 
                $context, 
                $parseTree, 
                $response
            );

    state: smartHomeControlPairingLamp
        intent!: /Home/Pairing_Lamp
        script:
            action_handler(
                "HOME_CONTROL_PAIRING_LAMP", 
                $context, 
                $parseTree, 
                $response
            );

    state: smartHomeControlPairingSocket
        intent!: /Home/Pairing_Socket
        script:
            action_handler(
                "HOME_CONTROL_PAIRING_SOCKET", 
                $context, 
                $parseTree, 
                $response
            );

    state: smartHomeCancelPostponed
        intent!: /Home/CancelPostponed
        script:
            action_handler(
                "HOME_CONTROL_CANCEL_POSTPONED", 
                $context, 
                $parseTree, 
                $response
            );

    state: smartHomeDontUnderstand
        event!: noMatch
        script:
            action_handler(
                "HOME_CONTROL_NO_MATCH_INTENT",
                $context,
                $parseTree,
                $response
            );
            
    state: sleepTimerOnboarding
        intent!: /Sleep_Timer/STOnboarding
        script:
            action_handler(
                "SLEEP_TIMER_ONBOARDING", 
                $context, 
                $parseTree, 
                $response
            );
            
    state: sleepTimerList
        intent!: /Sleep_Timer/STList
        script:
            action_handler(
                "SLEEP_TIMER_LIST", 
                $context, 
                $parseTree, 
                $response
            );
            
    state: sleepTimerInstantOff
        intent!: /Sleep_Timer/STInstantOff
        script:
            action_handler(
                "SLEEP_TIMER_INSTANT_OFF", 
                $context, 
                $parseTree, 
                $response
            );
        
    state: sleepTimerInstantOn
        intent!: /Sleep_Timer/STInstantOn
        script:
            action_handler(
                "SLEEP_TIMER_INSTANT_ON", 
                $context, 
                $parseTree, 
                $response
            );
            
    state: sleepTimerRemoveAll
        intent!: /Sleep_Timer/STRemoveAll
        script:
            action_handler(
                "SLEEP_TIMER_REMOVE_ALL", 
                $context, 
                $parseTree, 
                $response
            );
            
    state: sleepTimerRemove
        intent!: /Sleep_Timer/STRemove
        script:
            action_handler(
                "SLEEP_TIMER_REMOVE", 
                $context, 
                $parseTree, 
                $response
            );
            
    state: sleepTimerCreate
        intent!: /Sleep_Timer/STCreate
        script:
            action_handler(
                "SLEEP_TIMER_CREATE", 
                $context, 
                $parseTree, 
                $response
            );
    
    state: sleepTimerCreateExplicit
        intent!: /Sleep_Timer/STCreateExplicit
        script:
            action_handler(
                "SLEEP_TIMER_CREATE_EXPLICIT", 
                $context, 
                $parseTree, 
                $response
            );
            
    state: smartHomeControlSaturation
        intent!: /Home/Saturation
        script:
            action_handler(
                "HOME_CONTROL_SATURATION", 
                $context, 
                $parseTree, 
                $response
            );

    state: smartHomeControlSaturationLess
        intent!: /Home/Saturation_Less
        script:
            action_handler(
                "HOME_CONTROL_SATURATION_LESS", 
                $context, 
                $parseTree, 
                $response
            );

    state: smartHomeControlSaturationMore
        intent!: /Home/Saturation_More
        script:
            action_handler(
                "HOME_CONTROL_SATURATION_MORE", 
                $context, 
                $parseTree, 
                $response
            );

    state: TimeLimit
        event!: timeLimit
        a:  Извините, у меня что-то сломалось. Попробуйте другой вопрос.
