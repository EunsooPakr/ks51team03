package ks51team03.mail;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ks51team03.member.dto.Member;
import ks51team03.member.service.MemberService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;
    private final MemberService memberService;
    
    @ResponseBody
    @PostMapping("/mail")
    public String MailSend(String mail){

        int number = mailService.sendMail(mail);
        String num = "" + number;
        return num;
    }
    
    @ResponseBody
    @PostMapping("/findIdMail")
    public MailResult FindIdMailSend(String mail,String memberName){

        int number = mailService.sendMail(mail);
        MailResult mresult=new MailResult();
        String num = "" + number;
        mresult.setSend_result(num);
        
        Member member=new Member();
        member.setMemberEmail(mail);
        member.setMemberName(memberName);
        String findresult=memberService.getMemberIdByNameEmail(member);
        mresult.setFind_result(findresult);
        
        return mresult;
    }

    @ResponseBody
    @PostMapping("/findPwMail")
    public MailResult FindPwMailSend(String mail,String memberId){

        int number = mailService.sendMail(mail);
        MailResult mresult=new MailResult();
        String num = "" + number;
        mresult.setSend_result(num);
        
        Member member=new Member();
        member.setMemberEmail(mail);
        member.setMemberId(memberId);
        String findresult=memberService.getMemberPwByNameEmail(member);
        mresult.setFind_result(findresult);
        
        return mresult;
    }
}
