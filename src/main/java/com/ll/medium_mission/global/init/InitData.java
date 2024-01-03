package com.ll.medium_mission.global.init;

import com.ll.medium_mission.member.entity.Member;
import com.ll.medium_mission.member.service.MemberService;
import com.ll.medium_mission.post.entity.Post;
import com.ll.medium_mission.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!prod")
@RequiredArgsConstructor
public class InitData {

    private final MemberService memberService;

    static final String EMAIL = "ppusda1234@gmail.com";
    static final String PASSWORD = "ppusda1234";
    static final String FAQ_TITLE = "미디엄에서 글을 작성해보고 싶으신가요? 여기서 시작해보세요!";

    static final String FAQ_CONTENT = """
            # 좋은 글을 쓰기 위한 FAQ 방식의 가이드:
                        
            ## Q: 좋은 글을 쓰기 위한 첫 번째 단계는 무엇인가요?
            **A:** 좋은 글을 쓰기 위해 첫 번째로 해야 할 일은 목적과 대상 독자를 명확히 정하는 것입니다. 글을 쓰는 이유와 읽을 사람을 고려하여 글의 목표와 방향성을 설정하세요.
                        
            ## Q: 어떻게 목표와 대상 독자에 맞는 내용을 선택할 수 있나요?
            **A:** 대상 독자의 관심사와 수준을 파악하여 적절한 주제와 내용을 선택하세요. 독자가 흥미롭게 생각하고 더 알고 싶어하는 주제를 선택하면 좋은 글을 쓸 수 있습니다.
                        
            ## Q: 글을 쓸 때 어떤 구조를 사용해야 하나요?
            **A:** 글의 구조는 목차, 소제목, 단락 등을 활용하여 체계적으로 작성해야 합니다. 명확한 개요와 논리적인 흐름을 갖춘 구조를 사용하여 독자가 글을 이해하기 쉽게 만드세요.
                        
            ## Q: 어떻게 명확하고 간결한 문장을 작성할 수 있나요?
            **A:** 문장을 간결하고 명확하게 작성하기 위해 불필요한 단어와 구절을 제거하고, 직설적이고 명료한 표현을 사용하세요. 글을 읽는 사람이 이해하기 쉽도록 문장 구조와 어휘 선택에 신경을 쓰세요.
                        
            ## Q: 글을 쓸 때 어떻게 매력적인 제목을 만들 수 있나요?
            **A:** 매력적인 제목을 만들기 위해 독자의 호기심을 자극하거나 유용한 정보나 해결책을 암시하는 단어나 구절을 사용하세요. 제목은 글을 읽을 독자의 관심을 끌어야 하므로 신중하게 선택해야 합니다.
                        
            이러한 가이드라인을 따르면 좋은 글을 쓰는 데 도움이 될 것입니다. 물론, 글쓰기는 연습이 필요한 작업이므로 꾸준한 노력과 피드백을 통해 습관화하는 것이 중요합니다. 좋은 글쓰기를 위해 계속해서 노력해보세요!
            """;

    static final String TIP_TITLE = "읽기 좋은 글을 작성하는 방법!";

    static final String TIP_CONTENT = """
            # 사람들이 읽고 싶어하는 글을 작성하기 위한 몇 가지 팁:
                        
            1. **흥미로운 주제 선택:** 사람들이 흥미를 가지고 이야기를 읽기 위해서는 흥미로운 주제를 선택해야 합니다. 개인적인 경험, 독특한 관점, 사회적인 문제 등 독자들에게 호소할 수 있는 주제를 고려해 보세요.
                        
            2. **개인적인 관점과 경험 공유:** 에세이는 개인적인 경험과 관점을 통해 독자들과 소통하는 기회입니다. 자신의 감정, 생각, 경험을 솔직하게 표현하고 독자들과 공감할 수 있는 내용을 담아보세요.
                        
            3. **감정과 감각적인 요소 활용:** 에세이는 감정과 감각적인 요소를 통해 독자들의 감정에 다가갈 수 있는 작품입니다. 생생한 상황 묘사, 감정적인 에피소드, 풍부한 언어와 수사적인 표현 등을 활용하여 독자들의 상상력과 감정을 자극해 보세요.
                        
            4. **개인적인 성장과 교훈 제시:** 에세이는 독자들에게 새로운 관점이나 교훈을 제공할 수 있는 기회입니다. 자신의 경험에서 얻은 교훈이나 성장을 공유하고, 독자들도 함께 고민하고 생각해 볼 수 있는 질문을 제시해 보세요.
                        
            5. **문장 구조와 흐름에 신경 쓰기:** 에세이는 문장 구조와 흐름이 중요합니다. 글의 흐름을 잘 이어가며 명확하고 간결한 문장을 사용하세요. 단락을 활용하여 개별적인 아이디어를 체계적으로 전달하고, 글을 읽는 독자가 끊임없이 흥미를 느낄 수 있도록 구성해 보세요.
                        
            위의 팁을 활용하면 사람들이 읽고 싶어하는 에세이를 작성할 수 있을 것입니다. 또한, 글 작성은 계속해서 연습과 피드백을 통해 개선해 나가야 하는 과정이므로 꾸준한 노력과 열정을 가지고 도전해 보시기 바랍니다. 좋은 결과를 기대해 봅니다!
            """;

    static final String PAID_USER_EMAIL = "paid@gmail.com";
    static final String PAID_USER_PASSWORD = "paid1234";

    static final String PAID_TITLE = "유료 글";
    static final String PAID_CONTENT = "# 유료 내용";

    @Bean
    public CommandLineRunner demo(PostRepository postRepository) {
        memberService.join(EMAIL, null, PASSWORD);
        Member member = memberService.getMember("1");

        memberService.join(PAID_USER_EMAIL, null, PAID_USER_PASSWORD);
        memberService.registerMembership("2");
        Member paidMember = memberService.getMember("2");

        return (args) -> {
            postRepository.save(Post.builder()
                    .title(FAQ_TITLE)
                    .content(FAQ_CONTENT)
                    .author(member)
                    .build()
            );

            postRepository.save(Post.builder()
                    .title(TIP_TITLE)
                    .content(TIP_CONTENT)
                    .author(member)
                    .build()
            );

            for (int i = 1; i <= 100; i++) {
                postRepository.save(Post.builder()
                        .title(PAID_TITLE + i)
                        .content(PAID_CONTENT + i)
                        .isPaid(true)
                        .author(paidMember)
                        .build()
                );
            }
        };
    }
}
