export class UserDTO {
    constructor(nickname,password){
        this.nickname = nickname;
        this.password = password;
    }

    getNickname(){
        return this.nickname;
    }

    setNickname(nickname){
        this.nickname = nickname;
    }

    setPassword(password){
        this.password = password;
    }
}