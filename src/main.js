var X_SERVER_KEY = "Basic ---";
var PROM_PROJECT_ID = "352e8aa3-2d32-4992-b955-6ebdfdbf6da7";
var INT_URL= "https://assistant.homny.ru/assistentry/";
var COMMON_URL = "https://assistant2.homny.ru/assistentry/";
var PROD_URL = "https://assistentry.iot.sberdevices.ru/assistentry/";

var PREPROCCESS_COMMON_URL = "https://assistant2.homny.ru/assistentry/preprocess/";
var PREPROCCESS_PROD_URL = "https://assistentry.iot.sberdevices.ru/assistentry/preprocess/";

var deviceIDs = {
    "TST001": "test_user1",
    "testDeviceId": "test_user1"
}

function initPreProcess() {
    var pm = function (context) {
        var result = preprocessQuery(context, context.request.query);
        log("Preprocessed: " + result);
        context.request.query = result;
        if(!context.session.next) {
            $jsapi.startSession();
        }
    }
    bind("preMatch", pm);
}

function preprocessQuery(context, query) {
    var request = get_raw_request(context);
    var projectId = get_project_id(request);

    var body = {
        text: query,
        uuid: get_uuid(request)
    };

    var options = {
        dataType: "json",
        headers: {
            "Content-Type": "application/json",
            "X-Server-Key": X_SERVER_KEY
        },
        body: body,
    };
    
    var token = get_device_token(request);
    if(token != "") {
        body.request = replace_raw_sub(request, token)
    }
    
    var response = null;
    if(projectId == PROM_PROJECT_ID) {
        response = $http.post(PREPROCCESS_PROD_URL, options);
    } else {
        response = $http.post(PREPROCCESS_COMMON_URL, options);
    }
    
    try{
        if (response.isOk && response.data && response.data.result) {
            return response.data.result;
        }
    } catch(err) {
        log("Preprocess error:");
        log(err);
    }
    //log("Preprocessing error");
    //log(reponse);
    return query;
}



function get_raw_request(context){
    var request = (context || {}).request || {};
    var rawRequest = request.rawRequest || {};
    rawRequest.data = request.data || {};
    return rawRequest;
}

function get_character_id(request) {
    return (((request.payload || {}).character || {}).id || "");
}

function get_data(context){
    var payload = get_payload(context);
    if (payload.data &&
        payload.data.server_action){
            return payload.data.server_action;
    }
    return {};
}

function get_meta(context){
    var payload = get_payload(context);
    log("META:");
    log(payload.meta);
    return payload.meta;
}

function get_payload(context){
    if (context &&
        context.request &&
        context.request.rawRequest &&
        // context.request.rawRequest.raw &&
        context.request.rawRequest.payload){
            return context.request.rawRequest.payload;
        }
    return {};
}

function get_uuid(request) {
    if(request &&
       request.uuid) {
        return request.uuid;
       }
    return {};
}

function get_user_channel(request) {
    if(request &&
       request.uuid &&
       request.uuid.userChannel){
        return request.uuid.userChannel;
       }
    return "";
}

function get_device_token(request) {
    if (request) {
        if (request.payload && request.payload.device){
            var deviceID = request.payload.device.deviceId;
            if(deviceID in deviceIDs){
                return deviceIDs[deviceID];
            }
        }
        // my salute app
        if (request.uuid && request.uuid.userId) {
            if (request.uuid.userId == "905ac81c-7aa1-4420-ae40-1ce1fac1d9cc") {
                return deviceIDs["GZ190901849003721"];
            }
        }
    }
    return "";
}

function is_surface(request, surfaces) {
    if (request &&
        request.payload &&
        request.payload.device){
            var rSurface = request.payload.device.surface;
            rSurface = rSurface.toLowerCase();
            for(var i=0; i<surfaces.length;i++) {
                if(rSurface == surfaces[i]) {
                    return true;
                }
            }
        }
    return false;
}

function get_project_id(request) {
    if (request &&
        request.payload &&
        request.payload.app_info &&
        request.payload.app_info.projectId){
            return request.payload.app_info.projectId;
        }
    return "";
}

function replace_raw_sub(request, userId) {
    if (!request.uuid) {
        request["uuid"] = {};
    }
    //request.uuid["userId"] = userId;
    request.uuid["rawSub"] = userId;
    return request;
}

function prepareRequest(req) {
    var r = {
        messageId: req.messageId,
        uuid: req.uuid || {},
        payload: {},
        event_data: {},
    };
    r.uuid.sub = "";
    var payload = req.payload || {};
    var eventData = (req.data || {}).eventData || {};
    var message = payload.message || {};
    
    message = {
        original_text: message.original_text || "",
        asr_normalized_message: message.asr_normalized_message || ""
    };
    
    payload.message = message;
    // payload.annotations = {};
    // payload.asr = {};
    payload.character = {};
    
    r.payload = payload;
    r.event_data = eventData;

    return r;
}

function integration(event_id, context, request, parseTree) {
    var characterId = get_character_id(request)
    var body = {
        event: event_id,
        step: '',
        context_data: '',
        request: prepareRequest(request),
        parseTree: parseTree || {},
        meta: get_meta(context),
        characterId: characterId
    };

    if (context.session.next) {
        if(event_id != 'HELP_INFO_SERVER_ACTION') {
            body.event = context.session.next.intent;
            body.step = context.session.next.step;
            body.context_data = context.session.next.context_data;
        }
        delete context.session.next;
    }
    
    //log("Request:");
    //log(JSON.stringify(body));

    var options = {
        dataType: "json",
        headers: {
            "Content-Type": "application/json",
            "X-Server-Key": X_SERVER_KEY
        },
        body: body
    };
    var user_channel = get_user_channel(request);
    
    var token = get_device_token(request);
    if(token != "") {
        body.request = replace_raw_sub(body.request, token)
    }

    var response = null;
    if(user_channel != "B2B2C_HOTELS_ALTAY_RESORT") {
        var projectId = get_project_id(request);
        if(projectId == PROM_PROJECT_ID) {
            response = $http.post(PROD_URL, options);
        } else {
            response = $http.post(COMMON_URL, options);
        }
    } else {
        response = $http.post(INT_URL, options);
    }

    log("Response:");
    log(response);
    
    try {
        if (response.isOk && response.data && (response.data.text || response.data.deep_link || response.data.json)) {
            // log(response.data);
            // log("-- 1 --");
            return response.data;
        } else if (response.error) {
             log("-- 2 --");
             log(JSON.stringify(response))
            log("-- 2.1 --");
            log(response.error);
            var respErr = JSON.parse(response.error);
            if(isObject(respErr)) {
                // log("-- 2.2 --");
                return respErr;
            } else {
                // log("-- 2.3 --");
                // log(respErr.constructor);
            }
        }
        // log("-- 3 --");
    } catch (err) {
        log("Error on service request:");
        log(err);
        log(response);
    }
    
    return {};
}

function reply(body, response, session, isProd){
    var rtag = body._tag || ''
    if (body.next) {
        var items = [];
        if(!isProd) {
           items = [{response_tag: rtag}];
        }
        var reply = {
                type: "raw",
                messageName: "NOTHING_FOUND",
                body: {
                    // response_tag: rtag,
                    items: items
                }
            };
        response.replies = response.replies || [];
        response.replies.push(reply);
        return;
    }
    
    if (body.json) {
        log(">>> body.json");
        log(body.json);
        var responseBody = JSON.parse(body.json);
        var next_intent = responseBody.next_intent || "";
        var next_step = responseBody.next_step || "";
        var next_data = responseBody.next_data || '';
        if (next_intent != "" || next_step != "") {
            session.next = {
                intent: next_intent,
                step: next_step,
                context_data: next_data.toString(),
            }
        } else {
            delete session.next;
        }
        var reply = {
                type: "raw",
                body: responseBody
            };
        response.replies = response.replies || [];
        response.replies.push(reply);
        return;
    }

    var items = [];
    if(!isProd) {
       items = [{response_tag: rtag}];
    }
    var responseBody = {items: items};
    
    if (body.text) {
        if(!body.tts || body.tts.length() == 0) {
            body.tts = body.text;
        }
        responseBody.items.push(
            {
                bubble: {
                    text: body.text
                }
            }
        );
        responseBody.pronounceText = '<speak>' + body.tts + '</speak>';
        responseBody.pronounceTextType = "application/ssml";
        responseBody.finished = true;
        responseBody.auto_listening = false;
        if(!isProd) {
            responseBody.response_tag = rtag;
        }
    } else if (body.tts) {
        responseBody.pronounceText = '<speak>' + body.tts + '</speak>';
        responseBody.pronounceTextType = "application/ssml";
        responseBody.finished = true;
        responseBody.auto_listening = false;
        if(!isProd) {
            responseBody.response_tag = rtag;
        }
    }
    
    if (body.deep_link) {
        responseBody.items.push(
            {
                command: {
                    type: "action",
                    action: {
                        type: "deep_link",
                        deep_link: body.deep_link
                    }
                }
            }
        );
        responseBody.finished = true;
        responseBody.auto_listening = false;
    }
    if (body.text || body.deep_link) {
        var reply = {
            type: "raw",
            body: responseBody,
        };
        /* response.suggestions = {
            buttons: [
              {
                title: "👍👍",
                action: {
                  type: "text",
                  text: "лайк"
                }
              },
              {
                title: "Убить всех человеков",
                action: {
                  type: "text",
                  text: "kill"
                }
              }
            ]
        } */
        response.replies = response.replies || [];
        response.replies.push(reply);
    }
    
    if(!body.next && !body.text && !body.deep_link) {
        log('>>> Body error:');
        log(body);
        var msg = "Сегодня со мной что-то не так... Разработчики уже чинят.";
        var reply = {
            type: "text",
            text: msg,
            tts: msg
        };
        response.replies = response.replies || [];
        response.replies.push(reply);
    }
}

// Is it OBSOLETE?
function replyText(body, response){
    if (body.tts && body.text) {
        var reply = {
            type: "text",
            text: body.text,
            tts: body.tts
        };
        response.replies = response.replies || [];
        response.replies.push(reply);
        // response.replies.push({
        //     type: "raw",
        //     messageName: "NOTHING_FOUND",
        //     body: {}
        // });
    }
}

function replySimpleText(text, response){
    var reply = {
        type: "text",
        text: text,
        tts: text
    };
    response.replies = response.replies || [];
    response.replies.push(reply);
}

function action_handler(action_id, context, parseTree, response){
    var rawRequest = get_raw_request(context);
    var projectId = get_project_id(rawRequest);
    var isProd = (projectId == PROM_PROJECT_ID);

    var body = integration(action_id, context, rawRequest, parseTree);
    reply(body, response, context.session, isProd);
}

function openSmartHomeDeeplink(context, response) {
    var request = get_raw_request(context);
    var surfaces = ['sberbox', 'stargate', 'satellite', 'tv', 'tv_huawei'];
    if(is_surface(request, surfaces)) {
        openDeeplink("home://smart.home.promo?isStub=true&mainAppPackage=ru.sberdevices.smarthome.appmigration&action=mainscreen", response);
        return;
    }
    replySimpleText("Давай перейдем в сбербокс или портал, там я это умею.", response);
}

function openDeeplinkSurfaceConditional(context, response, surfaces, deepLink, fallbackText) {
    var request = get_raw_request(context);
    if(is_surface(request, surfaces)) {
        openDeeplink(deepLink, response);
        return;
    }
    replySimpleText(fallbackText, response);
}

function openDeeplink(deep_link, response) {
    var reply = {
        type: "raw",
        body: {
            items: [
                {
                    command: {
                        type: "action",
                        action: {
                            type: "deep_link",
                            deep_link: deep_link
                        }
                    }
                }
            ],
            finished: true,
            auto_listening: false
        }
    };
    
    response.replies = response.replies || [];
    response.replies.push(reply);
}

function isString(obj) {
    return obj !== undefined && obj !== null && obj.constructor == String;
}

function isObject(obj) {
    return obj !== undefined && obj !== null && obj.constructor == Object;
}

function toOnboardingString(replyType) {
    return 'ONBOARDING_'+replyType.toUpperCase()
}