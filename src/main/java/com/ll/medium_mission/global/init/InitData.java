package com.ll.medium_mission.global.init;

import com.ll.medium_mission.member.entity.Member;
import com.ll.medium_mission.member.service.MemberService;
import com.ll.medium_mission.post.entity.Post;
import com.ll.medium_mission.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitData {

    private final MemberService memberService;

    static final String email = "ppusda1234@gmail.com";
    static final String password = "ppusda1234";

    static final String FAQTitle = "Interested in Writing on Medium? Start Here.";

    static final String FAQContent = """
            You’ve seen stories published here on Medium,ou’ve seen stories published here on Medium, you’ve heard others who have published here talk about it, or you’ve found Medium while looking for places to write online. Either way, you might have some questions on how publishing works on Medium. We’ve gathered some information that can be helpful as you get started.
                        
            ✍ ️What’s your story?
            Who can write and publish?
            Anyone can! You don’t have to apply and be accepted to write. All you need to do is sign up. Once you’re signed up, start writing: medium.com/new-story.
                        
            Do I have to be vetted?
            You do not have to be vetted in any way to write on Medium. You don’t have to apply or take a writer’s quiz, or send a piece in to be considered. All you have to do is create an account.
                        
            What can be published on Medium?
            You can publish anything as long as it doesn’t violate the Medium Rules.
                        
            Who can see my stories?
            Once you’ve published a story, anyone who opens up the link can read it. Readers who follow you on Medium will see your stories in their “Following” section on their homepage. It can also show up in their Daily Digest email. If the story is curated, it will be distributed through recommendations, generally based on the reader’s interests. These recommendations show up on the homepage and the Daily Digest, and are unique to each reader.
                        
            I don’t know what to write about. What do I do?
            This happens to new writers and experienced writers, you’re not alone! You can see a few tips we’ve gathered from Medium writers.\s
                        
            1. What are your writing goals?
            2. What’s your “30-minute TED Talk”?
            3. How would your second-best friends describe you?
            4. What do you enjoy? (It’s the secret key to discovering your style.)
            5. What is realistic for you?
                        
            How do I grow my readership?
            Begin by building a regular cadence of writing, and then find your beat. You might not have an audience as you get started, remember — that’s part of the process. Promote your stories, and explore ways to connect with your readers beyond publishing. Take time to read and support other writers. Check out this growth guide as you get started.
                        
            What are publications on Medium?
            Publications are one of the ways stories are collected and distributed on Medium. Many publications on Medium accept submissions. Each publication has a different way of doing this. You can read more about publications and submitting to them in “If You Are Looking to Write for Publications, Start Here.”
                        
            Can I get paid to write?
            On Medium, writers have the opportunity to be paid through the Partner Program.
                        
            What posts are behind the paywall?
            A post is behind the paywall if its author has chosen to monetize it. Being behind the paywall means a reader has to be a paying Medium member in order to read it. Membership subscriptions pay the writers in the Partner Program. 
            """;

    static final String TipTitle = "How to Write an Essay Everyone Wants to Read";

    static final String TipContent = """
            Medium’s Creator Workshop Series, and we wanted to share some of the takeaways here. (The workshop was recorded, too — you can watch it here, and we’re embedding it below.) As editors at Human Parts and other publications, we’ve read (and edited) (and written!) a lot of personal essays. Here are some things we think are useful to keep in mind when crafting a work of creative nonfiction.
                        
            Of course, you probably aren’t actually going to go through these step-by-step — and almost certainly not all in the first draft. But if you eventually address each of these elements, you’ll be on your way to creating something great.
                        
            1. Be a noticer
            Also known as keep a notebook. But the notebook doesn’t have to be a notebook notebook. It can be the notes app in your phone, a scratch pad, a wad of scribbled-on envelopes in your pocket, or a stack of notecards. The important thing is to keep writing down observations and ideas and scraps of dialogue and sensory detail and stray thoughts so that eventually you will be able to see the patterns and connections. Read a ton, too; that’s part of it. Look at art, listen to music, watch movies, do freewriting — most of all, pay attention to the world.
                        
            Be a notebook, really.
                        
            By the way, this is a step to go back to every time you feel stuck or blocked. Get out there and experience and observe. (And then don’t forget to get back to the page eventually — the actual writing part is pretty key too.)
                        
            Writing exercise #1: Take five to 10 minutes to free-write a list of things you’ve noticed today or notice around you right now. Don’t edit or censor yourself or try to write sentences or make it pretty. Just list as many observations and details as you can.
                        
            2. Start with a question
            This doesn’t have to be an actual question, although it can be. But really we mean that it’s a good idea to start with an inclination or a feeling or something you’re personally trying to figure out. That’s what will keep you interested and motivated to finish. “Why can’t I stop thinking about X? I’m still trying to figure out how I feel about Y. How can I make sense of Z?”
                        
            How this manifests is most often in a messy first draft of something. That’s totally fine. If you knew from the start what you wanted to say, you would likely be writing an article or an instruction manual. While those things certainly have their places, a personal essay is a bit different. You’re probably not writing to share some knowledge. More likely, you’re writing toward the knowledge.
                        
            Maybe the larger lesson here is this: Don’t freak out if your first draft is really messy. It’s supposed to be. That’s why it’s just the first draft. Besides, if your first drafts were perfect, all the other writers would hate you.
                        
            3. Tell a story
            You’re going to have to forget everything you learned about five-paragraph essays in high school (Sorry, Mr. Wentz, but we’ve moved on.) You’re also going to have to shake off, a little bit, the classic craft advice to “show, don’t tell.” A great personal essay contains both showing and telling.
                        
            A great personal essay also, instead of proving a point, tells a story. If a traditional plot sounds like too much for what you’re trying to write, think instead in terms of movement or change. What will the reader know by the end of the essay that they didn’t at the beginning?
                        
            It’s important to maintain balance. (We mean in a piece of writing but probably also in life, come to think of it.) Scenes combined with summary. Showing combined with telling. Reserve the scene for the part that’s really important, the things you really want to show the reader because that’s where their attention should be.
                        
            This means you can open your essay the same way you would open a short story or a screenplay — with a scene or a scrap of dialogue. Just because it’s an essay doesn’t mean you don’t need it to be vivid, to paint a picture in the reader’s head.
                        
            While a bit of scene can be a great way into a piece, if you’re still in the drafting stage, just remember that you don’t have to start writing at the beginning. Don’t labor over your first line in a first draft. It probably won’t stay your first line anyway.
                        
            4. Write about you but for the reader
            In a personal essay, it’s probable that you, the writer, are also the main character. But the reader (most likely) has never met you. They aren’t going to necessarily start out on your side or even know what you’re talking about or where you’re coming from. You need to develop a persona for the page, and you can do this by taking a step back and looking at the piece as if this person were a character.
                        
            Don’t worry about voice or style. Just be honest. Everyone’s honesty is different, and that’s where the voice and style will come from. You’re the expert in being you. And if the reader senses you’re being honest, they will go anywhere you want to take them.
                        
            A lot of people don’t write this way because they don’t think they’re interesting. But everyone has a totally unique experience of the world and thus will have a totally unique and valuable story to share. On the flip side, some writers will plow into an essay assuming the reader knows what they, the writer, have been thinking about or experiencing. Nope. The reader is a stranger — a stranger, mind you, who has lots of other options for entertainment. Make it worth their while. Give them something they can take home with them, like a goody bag for the brain.
                        
            In other words, meet the reader where they are.
                        
            5. Remember there are two stories in every essay
            When we think of all the personal essays that have really made us feel, think, and get goosebumps, they almost always have a couple of different layers to them. Yes, there’s a personal story, but it connects to something larger, or else there’s some attention in the essay to what the personal story meant. Sometimes these are woven together in alternating sections. Sometimes the bigger picture only becomes clear in the last paragraph.
                        
            The “bigger picture” may be that part you didn’t quite know or fully understand when you were starting with your question. This is true of short stories and novels too, by the way — there’s often an internal (smaller, maybe invisible) conflict and an external (bigger, more obvious) conflict, and each reveals something about the other.
                        
            The big story is important because it often is the element of the piece that gives the reader something to really chew on.
                        
            Like with a lot of these steps we’re suggesting, this probably doesn’t all happen in the first draft. This is something to keep in mind as you go and then hone during revision. Sometimes you can’t figure out the connections right away, and you need to give it some time — maybe look at it the next day — or read it aloud or have someone else read it. You might have to write one more paragraph than you thought you needed.
                        
            If you do any of these writing exercises, post it on Medium and use the tag “SCW Essay.” Search that tag to read the work of fellow writers, too! We can’t wait to see what you come up with. And if you start to lose your nerve, just remember: Someone out there really wants to read your story. You’ve lived it. Now go write it.
      
            """;

    @Bean
    public CommandLineRunner demo(PostRepository postRepository) {
        memberService.join(email, password);
        Member member = memberService.getMember("1");

        return (args) -> {
            postRepository.save(Post.builder()
                    .title(FAQTitle)
                    .content(FAQContent)
                    .author(member)
                    .build()
            );

            postRepository.save(Post.builder()
                    .title(TipTitle)
                    .content(TipContent)
                    .author(member)
                    .build()
            );
        };
    }

}
