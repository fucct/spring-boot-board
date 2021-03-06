package spring.board.demo.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import spring.board.demo.domain.Article;
import spring.board.demo.dto.ArticleForm;
import spring.board.demo.dto.ArticleResponseDto;
import spring.board.demo.service.ArticleService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final ArticleService articleService;

    @GetMapping("/save")
    public String postForm(Model model) {
        ArticleForm articleForm = new ArticleForm();
        model.addAttribute("articleForm", articleForm);
        return "/posts-save";
    }

    @PostMapping("/save")
    public String post(@Valid ArticleForm articleForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "index";
        }
        Article article = Article.builder()
            .title(articleForm.getTitle())
            .nickName(articleForm.getNickName())
            .password(articleForm.getPassword())
            .content(articleForm.getContent())
            .build();

        Long articleId = articleService.save(article);
        return "redirect:/post/" + articleId;
    }

    @GetMapping("/{articleId}")
    public String readArticle(@PathVariable Long articleId, Model model) {
        ArticleResponseDto articleResponseDto = articleService.findById(articleId);
        model.addAttribute("article", articleResponseDto);
        return "posts-read";
    }
}
