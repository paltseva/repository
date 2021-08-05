
function action_handler_for_logging(action_id, context){
    var body = integration(action_id, context, get_raw_request(context), {});
}

// function onboarding_multi_reply(context, response, replyType) {
//     var request = get_raw_request(context);
//     action_handler_for_logging(
//                 'ONBOARDING_'+replyType.toUpperCase(),
//                 context
//             )
//     var salutSurfaces = ['stargate', 'companion', 'satellite'];
//     var reply;
//     switch (replyType) {
//         case 'lamp_pair_howto': // 1
//             reply = get_lamp_pair_howto_reply();
//             break;
//         case 'lamp_can_what': // 2
//             reply = get_lamp_can_what_reply(is_surface(request, salutSurfaces));
//             break;
//         case 'lamp_not_working': // 3
//             reply = get_lamp_not_working_reply();
//             break;
//         case 'socket_pair_howto': // 4
//             reply = get_socket_pair_howto_reply();
//             break;
//         case 'socket_can_what': // 5
//             reply = get_socket_can_what_reply(is_surface(request, salutSurfaces));
//             break;
//         case 'socket_not_working': // 6
//             reply = get_socket_not_working_reply();
//             break;
            
//         case 'ir_pair_howto': // 7
//             reply = get_default_reply();
//             break;
//         case 'ir_can_what': // 8
//             reply = get_default_reply();
//             break;
//         case 'ir_not_working': // 9
//             reply = get_default_reply();
//             break;
            
//         case 'smart_devices_more': // 10
//             reply = get_smart_devices_more_reply();
//             break;
//         case 'smart_home_can': // 11
//             reply = get_smart_home_can_reply(request);
//             break;
//         default:
//             reply = get_default_reply()
//             break;
//     };
    
//     reply.body.finished = true;
//     reply.body.auto_listening = false;

//     response.replies = response.replies || [];
//     response.replies.push(reply);
// }

// function get_default_reply() {
//     var items = prepare_items([{
//         top_text: "Мы еще работаем над этим...",
//         action_id: "HELP_INFO",
//         response_text: "Скоро здесь будет больше полезной информации."
//     }]);
//     return prepare_gallery_card(items);
// }

// function get_smart_home_can_reply(request) {
//     var characterName = request.payload.character.name;
//     var characterText;
//     switch (characterName) {
//         case 'Джой':
//             characterText = "Могу управлять освещением, менять яркость и цвет Умных ламп. Могу включить и выключить приборы, вставленные в Умные розетки. Долго перечислять всё";
//             break;
//         case 'Афина':
//             characterText = "Могу управлять освещением и настройками Умных ламп, включать и выключать устройства, вставленные в Умные розетки";
//             break;
//         default:
//             characterText = "Умный дом может сам включать, выключать и настраивать свет, кондиционер, телевизор, тостер — да что угодно! Но для этого тебе понадобятся: Умные лампы и пара Умных розеток от бренда Sber или другая совместимая со Sber техника";
//             break;
//     }
//     return prepare_text([prepare_text_item(characterText, false)], characterText);
// }

// function prepare_text_item(text, markdown) {
//     return {bubble: {type: "text", text: text, markdown: markdown}};
// }

// function prepare_text(items, pronounceText) {
//     var body = {items: items};
//     if (pronounceText) {
//         body.pronounceText = pronounceText;
//         body.pronounceTextType = "application/ssml";
//     }
//     return {type: "raw", body: body};
// }

function prepare_gallery_card(items) {
    return {
        type: "raw",
        body: {
            items: [{
                card: {
                    type: "gallery_card",
                    items: items
                }
            }]
        }
    };
}

// function get_lamp_pair_howto_reply() {
//     var items = prepare_items([
//         {
//             top_text: "Подключение лампы",
//             action_id: "HELP_INFO",
//             response_text: "1. Убедитесь, что устройство, с помощью которого вы настраиваете лампу, подключено к сети Wi-Fi 2,4 ГГц — той же, к которой будет подключаться лампа.\n2. Переведите лампу в режим настройки: вкл. — выкл., вкл. — выкл., вкл. Она быстро замигает.\n3. На SberPortal, SberBox или в приложении Сбер Салют откройте раздел умного дома и найдите там кнопку добавления лампы.\n4. Отобразятся инструкции. Следуйте им, чтобы завершить подключение. При этом убедитесь, что вводите верный пароль от Wi-Fi."
//         },
//         {
//             top_text: "Справка и поддержка",
//             action_id: "HELP_INFO",
//             response_text: "**Связаться с нами**\nПозвоните на номер [900](tel:900) (бесплатно с мобильных в России) или [+7&nbsp;495&nbsp;500-55-50](tel:+7(495)500-55-50) (из любой точки мира по тарифу вашего оператора связи).\n\n**Справка умного дома**\n[sberdevices.ru/help/smarthome/](https://sberdevices.ru/help/smarthome/)"
//         }
//     ]);
//     return prepare_gallery_card(items);
// }

// function get_socket_pair_howto_reply() {
//     var items = prepare_items([
//         {
//             top_text: "Подключение розетки",
//             action_id: "HELP_INFO",
//             response_text: "1. Убедитесь, что устройство, с помощью которого вы настраиваете розетку, подключено к сети Wi-Fi 2,4 ГГц — той же, к которой будет подключаться розетка.\n2. Нажмите кнопку на корпусе розетки и держите её — индикатор начнёт быстро мигать.\n3. На SberPortal, SberBox или в приложении Сбер Салют откройте раздел умного дома и найдите там кнопку добавления розетки.\n4. Отобразятся инструкции. Следуйте им, чтобы завершить подключение. При этом убедитесь, что вводите верный пароль от Wi-Fi."
//         },
//         {
//             top_text: "Справка и поддержка",
//             action_id: "HELP_INFO",
//             response_text: "**Связаться с нами**\nПозвоните на номер [900](tel:900) (бесплатно с мобильных в России) или [+7&nbsp;495&nbsp;500-55-50](tel:+7(495)500-55-50) (из любой точки мира по тарифу вашего оператора связи).\n\n**Справка умного дома**\n[sberdevices.ru/help/smarthome/](https://sberdevices.ru/help/smarthome/)"
//         }
//     ]);
//     return prepare_gallery_card(items);
// }

// function formQuote(needSalute, pair) {
//     if(needSalute) {
//         return pair[1] + "\n— **Салют, " + pair[0].toLowerCase() + "**";
//     }
//     return pair[1] + "\n— **" + pair[0] + "**";
// }

// function combineQuotes(needSalute, quotes) {
//     var result = "";
//     for(var i=0;i<quotes.length;i++) {
//         if(i>0) {
//             result += "\n\n";
//         }
//         result += formQuote(needSalute, quotes[i]);
//     }
//     return result;
// }

// function get_lamp_can_what_reply(needSalute) {
//     var items = prepare_items([{
//             top_text: "Управление освещением",
//             action_id: "HELP_INFO",
//             response_text: combineQuotes(needSalute,
//             [
//                 ["Включи торшер", "Включайте и выключайте"],
//                 ["Измени цвет лампы на жёлтый", "Управляйте цветом"],
//                 ["Убавь яркость ночника", "Меняйте яркость"],
//                 ["Сделай свет лампы теплее", "Делайте свет теплее или холоднее"],
//                 ["Запусти на бра романтику", "Включайте световые эффекты: северное сияние, романтику, имитацию свечи, рассвета, заката"]
//             ])
//         },
//         {
//             top_text: "Другие возможности",
//             action_id: "HELP_INFO",
//             response_text: combineQuotes(needSalute,
//             [
//                 ["Зажги светильник на кухне", "Уточняйте, к лампе в какой комнате относится команда"],
//                 ["Включи светильник в 7 утра", "Настраивайте таймер"],
//                 ["Горит ли настольная лампа?", "Узнавайте статус"],
//                 ["Выключи люстру", "Объединяйте лампы в дома, комнаты и группы"]
//             ])
//         },
//         {
//             top_text: "Узнать больше",
//             action_id: "HELP_INFO",
//             response_text: "Подробнее — в справке умного дома: [sberdevices.ru/help/smarthome/](https://sberdevices.ru/help/smarthome/)"
//     }]);

//     return prepare_gallery_card(items);
// }

// function get_socket_can_what_reply(needSalute) {
//     var items = prepare_items([
//         {
//             top_text: "Что умеет розетка",
//             action_id: "HELP_INFO",
//             response_text: combineQuotes(needSalute,
//             [
//                 ["Включи утюг", "Включайте и выключайте"],
//                 ["Включи тостер в 7 утра", "Настраивайте таймер"],
//                 ["Включи розетку на кухне", "Уточняйте, к розетке в какой комнате относится команда"],
//                 ["Работает ли увлажнитель?", "Узнавайте статус и мощность"],
//                 ["Заблокируй розетку", "Отключайте кнопку на корпусе"],
//                 ["Выключи видеотехнику", "Объединяйте розетки в дома, комнаты и группы"]
//             ])
//         },
//         {
//             top_text: "Узнать больше",
//             action_id: "HELP_INFO",
//             response_text: "Подробнее — в справке умного дома: [sberdevices.ru/help/smarthome/](https://sberdevices.ru/help/smarthome/)"
//         }
//     ]);
//     return prepare_gallery_card(items);
// }

// function get_lamp_not_working_reply() {
//     var items = prepare_items([
//         {
//             top_text: "Инструкция",
//             action_id: "HELP_INFO",
//             response_text: "- Проверьте, работает ли патрон.\n- Убедитесь, что в SberPortal, SberBox или приложении Сбер Салют вы авторизованы с тем же Сбер ID, с которым настраивали лампу.\n- Выйдите из приложения Сбер Салют, а затем войдите заново.\n- Проверьте, работает ли интернет.\n- Перезагрузите роутер.\n- Убедитесь, что на роутере выключен режим изоляции (гостевой режим)."
//         },
//         {
//             top_text: "Ничего не помогает",
//             action_id: "HELP_INFO",
//             response_text: "**Связаться с нами**\nПозвоните на номер [900](tel:900) (бесплатно с мобильных в России) или [+7&nbsp;495&nbsp;500-55-50](tel:+7(495)500-55-50) (из любой точки мира по тарифу вашего оператора связи).\n\n**Справка умного дома**\n[sberdevices.ru/help/smarthome/](https://sberdevices.ru/help/smarthome/)"
//         }
//     ]);
//     return prepare_gallery_card(items);
// }

// function get_socket_not_working_reply() {
//     var items = prepare_items([
//         {
//             top_text: "Инструкция",
//             action_id: "HELP_INFO",
//             response_text: "- Проверьте стационарную розетку, в которую включаете умную розетку.\n- Убедитесь, что в SberPortal, SberBox или приложении Сбер Салют вы авторизованы с тем же Сбер ID, с которым настраивали розетку.\n- Выйдите из приложения Сбер Салют, а затем войдите заново.\n- Проверьте, работает ли интернет.\n- Перезагрузите роутер.\n- Убедитесь, что на роутере выключен режим изоляции (гостевой режим)."
//         },
//         {
//             top_text: "Ничего не помогает",
//             action_id: "HELP_INFO",
//             response_text: "**Связаться с нами**\nПозвоните на номер [900](tel:900) (бесплатно с мобильных в России) или [+7&nbsp;495&nbsp;500-55-50](tel:+7(495)500-55-50) (из любой точки мира по тарифу вашего оператора связи).\n\n**Справка умного дома**\n[sberdevices.ru/help/smarthome/](https://sberdevices.ru/help/smarthome/)"
//         }
//     ]);
//     return prepare_gallery_card(items);
// }

// function get_smart_devices_more_reply() {
//         var items = prepare_items([
//         {
//             top_text: "Компоненты умного дома",
//             action_id: "HELP_INFO",
//             response_text: "**Умные лампы Sber (цоколь Е14 и E27)**\nЯрко светят и не мерцают даже на минимальной яркости. Управляются голосом: зажигаются, меняют яркость и цвет — только скажите\n\n**Умная розетка Sber**\nВключает и выключает электроприборы. А ещё — защищает детей и помогает экономить"
//         },
//         {
//             top_text: "Управляющие устройства",
//             action_id: "HELP_INFO",
//             response_text: "**SberPortal**\nСмарт-дисплей с акустикой и ассистентом. Умеет многое, в том числе&nbsp;— управлять умным домом\n\n**SberBox**\nМедиаприставка для ТВ с ассистентом. Показывает фильмы, сериалы, ТВ-каналы, ставит музыку, даёт поиграть и управляет умным домом"
//         },
//         {
//             top_text: "Узнать больше или купить",
//             action_id: "HELP_INFO",
//             response_text: "Подробности об устройствах и онлайн-магазин — на сайте [sberdevices.ru](https://sberdevices.ru)"//,
//             //response_image: "https://s-dt2.cloud.gcore.lu/smartmarket-smide-prod/447/448/VQxuv5sAXCgaZh5W.png",
//             //response_image_hash: "99d08da48ae7962b1fb070aff1ae26f3"
//         }
//     ]);
//     return prepare_gallery_card(items);
// }

// function prepare_items(items) {
//     var result = [];
//     for (var i = 0; i < items.length; ++i) {
//         var markdown = true;
//         if (items[i].response_markdown != undefined) {
//             markdown = items[i].response_markdown;
//         }
//         var item = {
//             type: "media_gallery_item",
//             image: {
//                 url: "//:0",
//                 size: {
//                     width: "medium",
//                     aspect_ratio: 0
//                 }
//             },
//             margins: {
//                 top: "4x",
//                 left: "6x",
//                 right: "6x",
//                 bottom: "5x"
//             },
//             top_text: {
//                 text: items[i].top_text,
//                 typeface: "title2",
//                 text_color: "default",
//                 max_lines: 0
//             }
//         };
//         if (items[i].bottom_text) {
//             item.bottom_text = {
//               text: items[i].bottom_text,
//               typeface: "footnote2",
//               text_color: "secondary",
//               max_lines: 0,
//               margins: {
//                 top: "8x"
//               }
//             };
//         }
//         if (items[i].action_id || items[i].response_text || items[i].response_image) {
//             item.actions = [{
//                 "type": "server_action",
//                 "message_name": "SERVER_ACTION",
//                 "server_action": {
//                     action_id: items[i].action_id,
//                     parameters: {
//                         text: items[i].response_text,
//                         image: items[i].response_image || "",
//                         image_hash: items[i].response_image_hash || "",
//                         markdown: markdown
//                     }
//                 }
//             }]
//         }
//         result.push(item);
//     }
//     return result;
// }

// function sendHelp(data, response) {
//     var items = [];
//     if ((data.text || "").length > 0 && (data.image || "").length == 0) {
//         items.push({bubble: {
//                         text: data.text || "",
//                         markdown: data.markdown
//                     }});
//     }
//     if ((data.image || "").length > 0 && (data.image_hash || "").length > 0) {
//         var cells = [];
//         if ((data.text || "").length > 0) {
//             cells.push({
//                 type: "text_cell_view",
//                 content: {
//                     text: data.text,
//                     typeface: "footnote1",
//                     text_color: "default",
//                     max_lines: 0
//                 },
//                 paddings: {
//                   left: "8x",
//                   top: "6x",
//                   bottom: "6x"
//                 }
//             });
//         }
//         cells.push({
//                     type: "image_cell_view",
//                     content: {
//                         url: data.image,
//                         hash: data.image_hash,
//                         height: 100,
//                         scale_mode: "scale_aspect_fit"
//                         // size: {
//                         //     width: "small",
//                         //     aspect_ratio: 1
//                         // }
//                     },
//                     paddings: {
//                       bottom: "6x"
//                     }
//         });
//         items.push({card: {
//                         type: "list_card",
//                         cells: cells
//         }});
//     }

//     var reply = {
//         type: "raw",
//         body: {items: items, finished: true, auto_listening: false}
//     };
//     response.replies = response.replies || [];
//     response.replies.push(reply);
// }