package com.aimall.entity.enums;

import com.aimall.utils.StringTools;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public enum PromptTypeEnum {

    GLOBAL("global", """
            # 瑙掕壊涓庢牳蹇冪洰鏍?
            浣犳槸鏅鸿兘璐墿鍔╂墜AI锛岃礋璐ｄ负鐢ㄦ埛鎻愪緵鍏ㄦ祦绋嬬數鍟嗘湇鍔°€備綘鐨勬牳蹇冪洰鏍囨槸锛氱簿鍑嗙悊瑙ｇ敤鎴烽渶姹傘€佹彁渚涗釜鎬у寲璐墿寤鸿銆侀珮鏁堝鐞嗚鍗曚笌鍞悗闂锛屽苟淇濇寔鍙嬪ソ涓撲笟鐨勬湇鍔℃€佸害銆?
            """, "鍏ㄥ眬绯荤粺鎻愮ず璇?),

    USER_INTENT("userIntent", """
            璇峰垎鏋愮敤鎴锋秷鎭殑璐墿鎰忓浘,骞舵彁鍙栧叧閿俊鎭€?
            
            鐢ㄦ埛ID锛?s
            鐢ㄦ埛闂: %s
            
            璇蜂粠浠ヤ笅鎰忓浘绫诲瀷涓€夋嫨鏈€鍖归厤鐨勪竴涓細
            INTENT TYPES:
            - PRODUCT_SEARCH: 鎼滅储鍟嗗搧銆佹兂涔颁笢瑗裤€佹煡鐪嬪晢鍝佷俊鎭紙濡傦細鎴戞兂涔版墜鏈猴紝鎵句竴涓嬭繛琛ｈ锛夛紝鍏抽敭淇℃伅杩斿洖鍏蜂綋鐨勫晢鍝佷俊鎭€?
            - QUERY_ORDER: 璁㈠崟鏌ヨ锛堝锛氭垜鐨勮鍗曪紵锛?濡傜敤鎴锋彁渚涜鍗曞彿锛堜緥濡傦細20251229161636SGUCPYYI1TXOEPHY锛夛紝灏嗚鍗曞彿瑙ｆ瀽鍑烘潵鐢╠ata杩斿洖,濡傛灉鏃犳硶瑙ｆ瀽璁㈠崟濂斤紝涓嶈鍐峝ata涓繑鍥?
            - REFUND: 閫€娆鹃€€璐х敵璇凤紙濡傦細鎴戞兂閫€娆撅紝鍟嗗搧鏈夐棶棰樿閫€璐э級
            - CANCEL_ORDER: 鍙栨秷宸蹭笅鍗曚絾鏈彂璐ф垨鏈畬鎴愮殑璁㈠崟锛堝锛氭垜涓嶆兂瑕佷簡锛屽府鎴戝彇娑堣鍗曪紝鍙栨秷鎴戝垰涔扮殑閭ｄ釜涓滆タ锛?
            - CONFIRM_RECEIPT: 鐢ㄦ埛纭宸叉敹鍒拌揣鐗╋紙濡傦細鎴戝凡缁忔敹鍒拌揣浜嗭紝鐐瑰摢閲岀‘璁ゆ敹璐э紝閭ｄ釜涓滆タ鎴戞嬁鍒颁簡锛?
            - QUERY_LOGISTICS: 鐗╂祦鏌ヨ銆佸揩閫掕窡韪€佸寘瑁圭姸鎬佹煡璇紙濡傦細鎴戠殑蹇€掑埌鍝簡锛熸煡涓€涓嬬墿娴侊紝璺熻釜鍖呰９锛?
            - PRODUCT_REVIEW: 鐢ㄦ埛涓诲姩瀵瑰凡鏀惰揣鍟嗗搧缁欏嚭璇勪环锛堝锛?缁欎笂涓鍗曞ソ璇?銆?杩欎笢瑗垮お宸簡锛屾垜瑕佽瘎浠?銆?缁欎竴涓ソ璇勫惂"銆?缁欎竴涓樊璇?锛?
            - CHAT: 涓€鑸€ч棶棰橈紝姣斿 闂€欍€佹墦鎷涘懠锛屽晢鍝佸姣旓紝璐墿缁嗗垯绛?
            
            杩斿洖JSON鏍煎紡锛屽寘鍚互涓嬪瓧娈碉細
            - intentType: 鎰忓浘绫诲瀷锛堜娇鐢ㄤ笂闈㈢殑绫诲瀷鍊硷級
            - data: 鍏抽敭淇℃伅锛屾瘮濡傝鍗曞彿锛堝鏋滃彲浠ユ彁鍙栧埌璁㈠崟鍙峰氨鎻愬彇锛屽鏋滄棤娉曟彁鍙栵紝灏变笉杩斿洖锛屼笉瑕侀殢渚挎崗閫犺鍗曞彿锛岃鍗曞彿绫讳技杩欐牱:20251229161636SGUCPYYI1TXOEPHY锛?
            绀轰緥鍝嶅簲锛?
            {
              "intentType": "PRODUCT_SEARCH",
              "data":"20251229161636SGUCPYYI1TXOEPHY"
            }
            """, "鐢ㄦ埛鎰忓浘鎻愮ず璇?),
    PRODUCT_SEARCH("product_search", """
            璇峰熀浜庝互涓嬪晢鍝佷俊鎭洖绛旂敤鎴烽棶棰橈細
            鍟嗗搧鏁版嵁锛?
            ```json
              %s
            ```
            鐢ㄦ埛闂锛?
            %s
            
            璇蜂弗鏍兼寜鐓т互涓嬭姹傛墽琛岋細
            1. 鍙繑鍥濲SON鏁扮粍锛屼笉瑕佹湁浠讳綍棰濆鏂囨湰
            2. JSON鏁扮粍涓瘡涓璞″彧鍖呭惈productId瀛楁
            3. 鍙娇鐢ㄦ彁渚涚殑鍟嗗搧淇℃伅涓殑ID锛屼笉瑕佽嚜宸辩紪閫營D
            4. 濡傛灉鐢ㄦ埛闂涓嶆槑纭垨娌℃湁鐩稿叧鍟嗗搧锛岃繑鍥炵┖鏁扮粍锛歔]
            5. 涓ユ牸鎸夌収浠ヤ笅鏍煎紡锛?
            [
              {"productId": "147099495496439"},
              {"productId": "326818100160840"}
            ]
            """, "鍟嗗搧鎼滅储"),
    QUERY_ORDER("query_order", "", "鏌ヨ璁㈠崟"),
    REFUND("refund", """
             澶勭悊閫€娆剧浉鍏抽棶棰樸€?
             鐢ㄦ埛ID锛?s
             鐢ㄦ埛闂:%s
             **绗竴姝ワ細璇嗗埆璁㈠崟鍙?*
             - 鐢ㄦ埛鍙栨秷璁㈠崟锛屽鏋滄棤娉曡В鏋愮敤鎴风殑璁㈠崟鍙凤紝璇风敤鎴锋彁渚涜鍗曚俊鎭?涓嶈鎹忛€犺鍗曞彿锛屾纭殑璁㈠崟鍙锋槸鏃堕棿鎴冲姞闅忔満瀛楁瘝锛堜緥濡傦細20251229161636SGUCPYYI1TXOEPHY锛?
             **绗簩姝ワ細璋冪敤宸ュ叿杩涜閫€娆?*
             - 鏍规嵁宸ュ叿杩斿洖缁撴灉锛屽弸濂藉憡鐭ョ敤鎴风粨鏋溿€?
            """, "閫€娆?),
    CANCEL_ORDER("cancel_order", """
             鐢ㄦ埛甯屾湜鍙栨秷涓€涓浜庘€滃緟浠樻鈥濄€佸彲鍙栨秷鐘舵€佺殑璁㈠崟銆傚吀鍨嬭〃杩板寘鎷細鈥滄垜瑕佸彇娑堣鍗曗€濄€佲€滀笉涔颁簡甯垜鍙栨秷鈥濄€佲€滃彇娑堟垜鍒氫笅鐨勫崟鈥濄€佲€滆鍗曞彿XXXX鎴戜笉瑕佷簡鈥濄€?
             鐢ㄦ埛ID锛?s
             鐢ㄦ埛闂:%s
             **绗竴姝ワ細璇嗗埆璁㈠崟鍙?*
             - 鐢ㄦ埛鍙栨秷璁㈠崟锛屽鏋滄棤娉曡В鏋愮敤鎴风殑璁㈠崟鍙凤紝璇风敤鎴锋彁渚涜鍗曚俊鎭?涓嶈鎹忛€犺鍗曞彿锛屾纭殑璁㈠崟鍙锋槸鏃堕棿鎴冲姞闅忔満瀛楁瘝锛堜緥濡傦細20251229161636SGUCPYYI1TXOEPHY锛?
             **绗簩姝ワ細璋冪敤宸ュ叿杩涜璁㈠崟鍙栨秷**
             - 鏍规嵁宸ュ叿杩斿洖缁撴灉锛屽弸濂藉憡鐭ョ敤鎴风粨鏋溿€?
            """, "鍙栨秷璁㈠崟"),
    CONFIRM_RECEIPT("confirm_receipt", """
             鐢ㄦ埛涓诲姩琛ㄧず宸叉敹鍒拌揣鐗╋紝骞跺彲鑳芥兂鎵嬪姩瀹屾垚鈥滅‘璁ゆ敹璐р€濇搷浣滀互缁撴潫浜ゆ槗娴佺▼銆傚吀鍨嬭〃杩板寘鎷細鈥滆揣宸叉敹鍒帮紝鎬庝箞纭鏀惰揣锛熲€濄€佲€滀笢瑗挎嬁鍒颁簡锛岀偣鍝噷纭锛熲€濄€佲€滄垜宸茬粡鏀跺埌鍖呰９浜嗏€?
             鐢ㄦ埛ID锛?s
             鐢ㄦ埛闂:%s
             **绗竴姝ワ細璇嗗埆璁㈠崟鍙?*
             - 鐢ㄦ埛纭鏀惰揣锛屽鏋滄棤娉曡В鏋愮敤鎴风殑璁㈠崟鍙凤紝璇风敤鎴锋彁渚涜鍗曚俊鎭?涓嶈鎹忛€犺鍗曞彿锛屾纭殑璁㈠崟鍙锋槸鏃堕棿鎴冲姞闅忔満瀛楁瘝锛堜緥濡傦細20251229161636SGUCPYYI1TXOEPHY锛?
             **绗簩姝ワ細璋冪敤宸ュ叿杩涜纭鏀惰揣**
             - 鏍规嵁宸ュ叿杩斿洖缁撴灉锛屽弸濂藉憡鐭ョ敤鎴风粨鏋溿€?
            """, "纭鏀惰揣"),
    QUERY_LOGISTICS("query_logistics", """
            鏌ヨ鐗╂祦淇℃伅銆?
            鐢ㄦ埛ID锛?s
            鐢ㄦ埛闂锛?s
             **绗竴姝ワ細璇嗗埆璁㈠崟鍙?*
             - 鐢ㄦ埛鏌ヨ璁㈠崟鐗╂祦锛屽鏋滄棤娉曡В鏋愮敤鎴风殑璁㈠崟鍙凤紝璇风敤鎴锋彁渚涜鍗曚俊鎭?涓嶈鎹忛€犺鍗曞彿锛屾纭殑璁㈠崟鍙锋槸鏃堕棿鎴冲姞闅忔満瀛楁瘝锛堜緥濡傦細20251229161636SGUCPYYI1TXOEPHY锛?
             **绗簩姝ワ細璋冪敤宸ュ叿鏌ヨ鐗╂祦淇℃伅**
             - 鏍规嵁宸ュ叿杩斿洖缁撴灉锛屽弸濂藉憡鐭ョ敤鎴疯缁嗙殑鐗╂祦淇℃伅銆?
            """, "鏌ヨ鐗╂祦淇℃伅"),
    PRODUCT_REVIEW("product_review", """
            澶勭悊璁㈠崟璇勪环銆?
            鐢ㄦ埛ID锛?s
            鐢ㄦ埛淇℃伅锛?s
            
            涓夋澶勭悊锛?
            
            1. **淇℃伅鎻愬彇**
               - 璁㈠崟ID锛氫粠闂涓彁鍙栨垨寮曞鎻愪緵
               - 璇勪环鍐呭锛氱敤鎴峰鍟嗗搧鐨勬弿杩?
            
            2. **鑷姩璇勬槦锛堝熀浜庢儏鎰熷垎鏋愶級**
               猸?1鏄燂細闈炲父璐熼潰锛堝瀮鍦俱€佺碂绯曘€佸樊璇勶級
               猸愨瓙 2鏄燂細璐熼潰锛堝け鏈涖€佷笉濂姐€佸悗鎮旓級
               猸愨瓙猸?3鏄燂細涓€э紙杩樿銆佷竴鑸€侀┈椹檸铏庯級
               猸愨瓙猸愨瓙 4鏄燂細姝ｉ潰锛堜笉閿欍€佹尯濂姐€佹弧鎰忥級
               猸愨瓙猸愨瓙猸?5鏄燂細闈炲父姝ｉ潰锛堝畬缇庛€佽秴妫掋€佸己鐑堟帹鑽愶級
            """, "璇勪环璁㈠崟"),
    CHAT("chat", """
             璇峰熀浜庝互涓嬬煡璇嗗簱鍐呭鍥炵瓟鐢ㄦ埛闂锛屼繚鎸佸弸濂戒笓涓氾細
             鍥炵瓟瑕佹眰锛?
             1. 鍙熀浜庢彁渚涚殑鐭ヨ瘑搴撳唴瀹瑰洖绛旓紝涓嶈缂栭€犱俊鎭?
             2. 鍥炵瓟瑕佷笓涓氥€佸弸濂姐€佺畝娲?
             3. 瀵逛簬鏀跨瓥绫婚棶棰橈紝闇€瑕佹槑纭鏄庢潯浠跺拰闄愬埗
             4. 濡傛灉娑夊強澶氫釜鏂归潰锛岃鍒嗙偣璇存槑
             5. 鐭ヨ瘑搴撲腑鍖呭惈鍥剧墖淇℃伅涔熶竴骞惰繑鍥炰笉瑕佽繃婊?
             鐭ヨ瘑搴撳唴瀹癸細
             %s
            
             鐢ㄦ埛ID锛?s
            
             鐢ㄦ埛闂:%s
            
            """, "涓€鑸€ч棶棰?),
    ;

    private String key;
    private String prompt;
    private String desc;

    PromptTypeEnum(String key, String prompt, String desc) {
        this.key = key;
        this.prompt = prompt;
        this.desc = desc;
    }

    public String getKey() {
        return key;
    }

    public String getPrompt() {
        return prompt;
    }

    public String getDesc() {
        return desc;
    }

    public static PromptTypeEnum getByCode(String code) {
        Optional<PromptTypeEnum> typeEnum = Arrays.stream(PromptTypeEnum.values()).filter(value -> value.toString().equals(code)).findFirst();
        return typeEnum == null || typeEnum.isEmpty() ? null : typeEnum.get();
    }

    public static PromptTypeEnum getByKey(String key) {
        Optional<PromptTypeEnum> typeEnum = Arrays.stream(PromptTypeEnum.values()).filter(value -> value.getKey().equals(key)).findFirst();
        return typeEnum == null || typeEnum.isEmpty() ? null : typeEnum.get();
    }


    public record Prompt(String key, String prompt, String desc) {
        public static Prompt of(PromptTypeEnum typeEnum) {
            return new Prompt(typeEnum.getKey(), typeEnum.getPrompt(), typeEnum.getDesc());
        }
    }

    public static List<Prompt> getPrompts() {
        return Arrays.stream(PromptTypeEnum.values()).filter(value -> !StringTools.isEmpty(value.getPrompt())).map(Prompt::of).toList();
    }
}

