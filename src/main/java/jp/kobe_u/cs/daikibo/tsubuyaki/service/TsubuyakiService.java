package jp.kobe_u.cs.daikibo.tsubuyaki.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.kobe_u.cs.daikibo.tsubuyaki.entity.Tsubuyaki;
import jp.kobe_u.cs.daikibo.tsubuyaki.repository.TsubuyakiRepository;

@Service
public class TsubuyakiService {
    @Autowired
    TsubuyakiRepository repo; // レポジトリ

    // つぶやきを投稿
    public Tsubuyaki postTsubuyaki(Tsubuyaki t) {
        // 名前がない場合の業務ロジック
        String name = t.getName();
        if (name == null || name.length() == 0) {
            t.setName("名無しさん");
        }
        t.setCreatedAt(new Date()); // 作成日時をセット
        return repo.save(t); // セーブしたオブジェクトを返却
    }

    // 全つぶやきを取得
    public List<Tsubuyaki> getAllTsubuyaki() {
        Iterable<Tsubuyaki> found = repo.findAll();
        ArrayList<Tsubuyaki> list = new ArrayList<>();
        found.forEach(list::add);
        return list;
    }

    // つぶやきを検索
    public List<Tsubuyaki> searchTsubuyaki(String keyword) {
        return repo.findByCommentContaining(keyword);
    }
}
