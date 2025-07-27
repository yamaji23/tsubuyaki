package jp.kobe_u.cs.daikibo.tsubuyaki.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.kobe_u.cs.daikibo.tsubuyaki.entity.Tsubuyaki;
import jp.kobe_u.cs.daikibo.tsubuyaki.service.TsubuyakiService;

@Controller
public class TsubuyakiController {

    @Autowired
    TsubuyakiService ts;

    // タイトル画面を表示
    @GetMapping("/")
    String showIndex() {
        return "index";
    }

    // メイン画面を表示
    @GetMapping("/read")
    String showTsubuyakiList(Model model, @RequestParam(name = "keyword", required = false) String keyword) {
        List<Tsubuyaki> list;
        if (keyword != null && !keyword.isEmpty()) {
            list = ts.searchTsubuyaki(keyword);
        } else {
            list = ts.getAllTsubuyaki(); // 全つぶやきを取得
        }
        model.addAttribute("tsubuyakiList", list); // モデル属性にリストをセット
        model.addAttribute("tsubuyakiForm", new TsubuyakiForm()); // 空フォームをセット
        return "tsubuyaki_list"; // リスト画面を返す
    }

    // つぶやきを投稿
    @PostMapping("/read")
    String postTsubuyaki(@Validated @ModelAttribute("tsubuyakiForm") TsubuyakiForm form, BindingResult bindingResult, Model model) {
        // 入力チェックに引っかかった場合、メイン画面に戻る
        if (bindingResult.hasErrors()) {
            // GETリクエスト用のメソッドを呼び出して、メイン画面に戻る
            return showTsubuyakiList(model, null);
        }
        
        // フォームからエンティティに移し替え
        Tsubuyaki t = new Tsubuyaki();
        t.setName(form.getName());
        t.setComment(form.getComment());

        // サービスに投稿処理を依頼
        ts.postTsubuyaki(t);

        return "redirect:/read"; // メイン画面に転送
    }
}
