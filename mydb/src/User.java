public class User {
    private String username;
    float wallet;
    public User (String username,float wallet)
    {
        this.wallet = wallet;
        this.username = username;
    }
    public String getUsername()
    {
        return this.username;
    }
    public void setWallet(float change)
    {
        this.wallet = this.wallet + change;
    }
    public float getWallet()
    {
        return this.wallet;
    }
}
