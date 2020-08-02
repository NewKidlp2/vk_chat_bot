package Bot;

enum Urls {
    deleteCallBackServerUrl("https://api.vk.com/method/groups.deleteCallbackServer"),
    getCallbackConfirmationCodeUrl("https://api.vk.com/method/groups.getCallbackConfirmationCode"),
    addCallbackServerUrl("https://api.vk.com/method/groups.addCallbackServer"),
    setCallbackServerSettingsUrl("https://api.vk.com/method/groups.setCallbackSettings"),
    sendMessageUrl("https://api.vk.com/method/messages.send");

    private String url;
    Urls(String url) {
        this.url = url;
    }

    public String getUrl () {
        return url;
    }
}
