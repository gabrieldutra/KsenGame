package entity;


import entity.Element;
import connection.Connection;
import connection.Server;
import db.CharacterDAO;
import java.util.Random;

/**
 *
 * @author gabriel
 */
public class Character extends Element implements Comparable<Character> {
    private int id;
    private String nome;
    private int vida=100;
    private int mana=100;
    private int level=1;
    private int points=0;
    private int ataque=5;
    private int defesa=0;
    private int precisao=70;
    private int critico=10;
    private int countVida=0;
    private int classe=1;
    private int sprintcount=0;
    private int score=0;
    private int firecooldown=0;
    private float velocidade=1;
    private Connection conexao;
    private boolean attacking=false;
    private int sword=0;
    private int direction=0;
    private int slowedTime = 0;
    
    public Character(){
        this.setWidth(32);
        this.setHeight(32);
    }
   
    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the vida
     */
    public int getVida() {
        return vida;
    }

    /**
     * @param vida the vida to set
     */
    public void setVida(int vida) {
        this.vida = vida;
    }

    /**
     * @return the ataque
     */
    public int getAtaque() {
        return ataque;
    }

    /**
     * @param ataque the ataque to set
     */
    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    /**
     * @return the precisao
     */
    public int getPrecisao() {
        return precisao;
    }

    /**
     * @param precisao the precisao to set
     */
    public void setPrecisao(int precisao) {
        this.precisao = precisao;
    }

    /**
     * @return the velocidade
     */
    public float getVelocidade() {
        return velocidade;
    }

    /**
     * @param velocidade the velocidade to set
     */
    public void setVelocidade(float velocidade) {
        this.velocidade = velocidade;
    }

    /**
     * @return the classe
     */
    public int getClasse() {
        return classe;
    }
    
    public String getClassName(){
        if(this.classe == 1) return "Ninja";
        if(this.classe == 2) return "Mago";
        if(this.classe == 3) return "Guerreiro";
        return "Desconhecido";
    }
    
    public String getClassSpecial(){
        if(this.classe == 1) return "Alta velocidade.";
        if(this.classe == 2) return "Alto poder mágico.";
        if(this.classe == 3) return "Alta defesa e dano médio.";
        return "Nenhum";
    }
    
    public String getClassAttack(){
        if(this.classe == 1) return "Investida [SHIFT] - Kunais [ESPAÇO].";
        if(this.classe == 2) return "Teleporte [SHIFT] - Bola de Fogo [ESPAÇO].";
        if(this.classe == 3) return "Trocar Espada [SHIFT] - Atacar [ESPAÇO].";
        return "Nenhum";
    }

    /**
     * @param classe the classe to set
     */
    public void setClasse(int classe) {
        this.classe = classe;
        // Classe 1 - Ninja
        if(classe == 1){
            this.velocidade=2;
            this.ataque=30;
            this.precisao=80;
        }
        
        // Classe 2 - Mago
        if(classe == 2){
            this.velocidade=(float) 1.5;
            this.ataque=40;
            this.precisao=70;
        }
        
        // Classe 3 - Guerreiro
        if(classe == 3){
            this.velocidade=(float) 1.7;
            this.ataque=30;
            this.defesa=15;
            this.critico=15;
            this.precisao=80;
        }
    }

    /**
     * @return the conexao
     */
    public Connection getConexao() {
        return conexao;
    }

    /**
     * @param conexao the conexao to set
     */
    public void setConexao(Connection conexao) {
        this.conexao = conexao;
    }

    /**
     * @return the attacking
     */
    public boolean isAttacking() {
        return attacking;
    }

    /**
     * @param attacking the attacking to set
     */
    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    /**
     * @return the mana
     */
    public int getMana() {
        return mana;
    }

    /**
     * @param mana the mana to set
     */
    public void setMana(int mana) {
        this.mana = mana;
    }

    /**
     * @return the sprintcount
     */
    public int getSprintcount() {
        return sprintcount;
    }

    /**
     * @param sprintcount the sprintcount to set
     */
    public void setSprintcount(int sprintcount) {
        this.sprintcount = sprintcount;
    }

    /**
     * @return the countVida
     */
    public int getCountVida() {
        return countVida;
    }

    /**
     * @param countVida the countVida to set
     */
    public void setCountVida(int countVida) {
        this.countVida = countVida;
    }

    /**
     * @return the firecooldown
     */
    public int getFirecooldown() {
        return firecooldown;
    }

    /**
     * @param firecooldown the firecooldown to set
     */
    public void setFirecooldown(int firecooldown) {
        this.firecooldown = firecooldown;
    }
    
    public void spawn(){
        this.setVida(100);
        this.setMana(100);
        this.setAttacking(false);
        this.setSlowedTime(0);
        boolean colision;
        do{
            colision=false;
            this.setPosX(Math.abs(new Random().nextInt()%900)-300);
            this.setPosY(-1*(Math.abs(new Random().nextInt()%900)-200));
            for(Character p : Server.getPersonagens()){
                if(this.checaColisao(p) && p != this) colision=true;
            }
            for(MapElement m : Server.getElements()){
               if(this.checaColisao(m)) colision=true;
            }
        } while(colision);
    }
    
    public void upAttribute(String attribute){
        if(this.getPoints() > 0){
            switch(attribute){
                case "attack":
                    if(this.getAtaque() < 100){                        
                        this.points--;
                        this.ataque+=5;                  
                    } else this.getConexao().sendMessage("MSG:Server:Seu ataque já atingiu o máximo!"+"\\");
                break;
                case "defense":
                    if(this.getDefesa() < 50){
                        this.points--;
                        this.defesa+=5;
                    }else this.getConexao().sendMessage("MSG:Server:Sua defesa já atingiu o máximo!"+"\\");
                break;
                case "dexterity":
                    if(this.getPrecisao() < 100){
                        this.points--;
                        this.precisao+=5;
                    }else this.getConexao().sendMessage("MSG:Server:Sua precisão já atingiu o máximo!"+"\\");
                break;
                case "critical":
                    if(this.getCritico() < 50){
                        this.points--;
                        this.critico+=5;
                    }else this.getConexao().sendMessage("MSG:Server:Seu crítico já atingiu o máximo!"+"\\");
                break;
                case "speed":
                    if(this.getVelocidade() < 3.0){
                        if(this.getSprintcount() == 0){                            
                            this.points--;
                            this.velocidade+=(float)0.1;
                        }else this.getConexao().sendMessage("MSG:Server:Espere o efeito da Investida passar!"+"\\");
                    }else this.getConexao().sendMessage("MSG:Server:Sua velocidade já atingiu o máximo!"+"\\");
                break;

            }
            CharacterDAO cdao = Server.cdao;
            cdao.update(this);
        }
        
    }
    
    public void checkKill(Character killer){
        if(this.getVida() <= 0){
            killer.setScore(killer.getScore()+1);
            CharacterDAO cdao = Server.cdao;
            cdao.update(killer);
            Server.sendMessageToAll("MSG:Server:"+killer.getNome()+" killed "+this.getNome()+"!\\");                              
            Server.soundToWhoCanSee(this, "res/sound/maledie.ogg");
            this.getConexao().sendMessage("USP:res/sound/nelson.ogg\\");
            this.spawn();                            
        }
    }
    
    public boolean canSee(Element e){
        float plX = e.getPosX();
        float pX = this.getPosX();
        float plY = e.getPosY();
        float pY = this.getPosY();
        int shiftX = 320;
        int shiftY = 240;
        boolean visible=plX > (pX-shiftX-32) && plX < (pX-shiftX)+640 && plY > (pY-shiftX) && plY < (pY-shiftY)+480;
        return visible;
    }

    /**
     * @return the score
     */
    public int getScore() {
        return score;
    }

    /**
     * @param score the score to set
     */
    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public int compareTo(Character o) {
        Integer scT = this.level;
        Integer scO = o.level;
        if(this.level == o.level){
            scT = this.score;
            scO = o.score;
        }
        return scT.compareTo(scO);
    }
    
    public int calculateDamage(Character k){
        return this.calculateDamage(1,k);
    }
    
    public int calculateDamage(float basem, Character k){
        Random ran = new Random();
        float baseatk=this.getAtaque()*basem;
        float basedef=k.getDefesa()*basem;
        float atkgarantido=baseatk*(float)(this.getPrecisao()/100.0);
        float luck=(float)((Math.abs(ran.nextInt())%101)/100.0);
        float atksorte=luck*(baseatk-atkgarantido);
        float critic=0;
        if(Math.abs(ran.nextInt())%100 < this.getCritico()) critic=atkgarantido+atksorte;
        if((int)((atkgarantido+atksorte+critic)-basedef) <= 0) return 1;
        return (int)((atkgarantido+atksorte+critic)-basedef);
    }

    /**
     * @return the critico
     */
    public int getCritico() {
        return critico;
    }

    /**
     * @param critico the critico to set
     */
    public void setCritico(int critico) {
        this.critico = critico;
    }

    /**
     * @return the defesa
     */
    public int getDefesa() {
        return defesa;
    }

    /**
     * @param defesa the defesa to set
     */
    public void setDefesa(int defesa) {
        this.defesa = defesa;
    }

    /**
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * @param level the level to set
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * @return the points
     */
    public int getPoints() {
        return points;
    }

    /**
     * @param points the points to set
     */
    public void setPoints(int points) {
        this.points = points;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the sword
     */
    public int getSword() {
        return sword;
    }

    /**
     * @param sword the sword to set
     */
    public void setSword(int sword) {
        this.sword = sword;
    }

    /**
     * @return the direction
     */
    public int getDirection() {
        return direction;
    }

    /**
     * @param direction the direction to set
     */
    public void setDirection(int direction) {
        this.direction = direction;
    }

    /**
     * @return the slowedTime
     */
    public int getSlowedTime() {
        return slowedTime;
    }

    /**
     * @param slowedTime the slowedTime to set
     */
    public void setSlowedTime(int slowedTime) {
        this.slowedTime = slowedTime;
    }

 

}
