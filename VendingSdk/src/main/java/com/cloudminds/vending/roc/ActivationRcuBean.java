package com.cloudminds.vending.roc;

/**
 * 激活成功时候，返回的Bean
 */
public class ActivationRcuBean {
    /**
     * sys : ROC
     * code : 0
     * messages : OK
     * data : {"tenantCode":"10086","userCode":"arvin","serviceCode":"bank","vpnProfile":"client\r\ndev tun\r\nproto tcp\r\nsndbuf 0\r\nrcvbuf 0\r\nremote vpn.cloudminds-inc.com 443\r\nresolv-retry infinite\r\nnobind\r\npersist-key\r\npersist-tun\r\nremote-cert-tls server\r\ncipher AES-128-CBC\r\ncomp-lzo\r\nsetenv opt block-outside-dns\r\nkey-direction 1\r\nverb 3\r\n<ca>\r\n-----BEGIN CERTIFICATE-----\nMIIDMjCCAhqgAwIBAgIJAJ1eyH9UUDuFMA0GCSqGSIb3DQEBCwUAMBUxEzARBgNV\nBAMTCkNsb3VkTWluZHMwHhcNMTYxMTI0MDYxMzM3WhcNMjYxMTIyMDYxMzM3WjAV\nMRMwEQYDVQQDEwpDbG91ZE1pbmRzMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIB\nCgKCAQEAuRrnVKT7A51yynz3alYUBu8upfcNmGMI6AlOodC3BQVjhG2ZNWEqfhoL\n+pc+KB6xmsh7BJBtE0UC1mDZS6maw5TEuzibKN1eAkIGL0sDVp/iApWVJxa6jYIh\nIgyrLMdinw+BdAli+9RzBBV5J9ypbKgDUTcgxvg9d2IRBJaCIv3KcUZ5DywVKXVl\nTD8VO63WkdwT/LUl9UF4BbZ2h6TkOnALF77iZdww0n66U5aZ1szMAtvigeWnNm9Y\nwqHZCFV8ZDOmYD+2v6E1KAyZ2AqYaMPm37razLnsVx0jaS3J/FoJ/Ltc0Z6HgYDP\nlAeyxPoMNaRD5eLm/P1+VxULnG5AewIDAQABo4GEMIGBMB0GA1UdDgQWBBQHpBPt\n+o7rfXq6F97/vn8JfODlxjBFBgNVHSMEPjA8gBQHpBPt+o7rfXq6F97/vn8JfODl\nxqEZpBcwFTETMBEGA1UEAxMKQ2xvdWRNaW5kc4IJAJ1eyH9UUDuFMAwGA1UdEwQF\nMAMBAf8wCwYDVR0PBAQDAgEGMA0GCSqGSIb3DQEBCwUAA4IBAQA9Gk+IDl7iHGZ5\nXyABkzxCEfgnFG3U+9ZPYwpKBpZUB8Q3Zr8AWsEGSK5xMiBF60V6p9pB1gL6cg1q\nYCY/+KSHQnzfmXowjV+J9pRfc+aE8XwV4yAMPEiWW7U1cdTG2LErA0tngITB0IZS\n7XTi1fvLobA+MUkOsO7+XEu/AuT695k6KbV5oWmE0winkA3lYUoToLd4+yCGXkYS\nhSX+kGou0d2tBEOUB4wqGP0XUA0DH1VURDEGQIMZhpdyOyoD1ggEp7i2HBxWwAsu\n5Puwfl4XMjDji0zYLkTMlWKohfEslAbOPXtR+CG0/ys3rdCBiUgwqIX/qLorcYHx\nvjfsSUwq\n-----END CERTIFICATE-----\n\r\n<\/ca>\r\n<cert>\r\nCertificate:\n    Data:\n        Version: 3 (0x2)\n        Serial Number: 53 (0x35)\n    Signature Algorithm: sha256WithRSAEncryption\n        Issuer: CN=CloudMinds\n        Validity\n            Not Before: Dec 14 10:07:57 2016 GMT\n            Not After : Dec 12 10:07:57 2026 GMT\n        Subject: CN=jane.li\n        Subject Public Key Info:\n            Public Key Algorithm: rsaEncryption\n                Public-Key: (2048 bit)\n                Modulus:\n                    00:99:93:05:4c:98:28:cd:34:84:97:be:e6:14:d1:\n                    66:42:3c:8a:04:37:51:49:52:d7:5a:f4:a8:63:a4:\n                    ca:cd:10:fe:96:c6:bc:ad:7c:fc:fc:e0:f8:ba:f8:\n                    d1:05:25:92:50:59:d4:38:39:7f:f8:53:cf:1a:23:\n                    39:8b:24:ad:7d:e5:24:69:7d:e2:24:77:cb:b7:a5:\n                    8c:19:74:c5:56:3e:da:65:d2:38:c8:7d:15:27:a9:\n                    ab:a1:b3:6d:8a:3f:8b:6a:86:77:00:39:0e:7e:1d:\n                    49:a5:65:2b:c8:74:b8:55:ec:9b:37:37:99:e2:45:\n                    ac:e1:b9:10:a7:14:ef:20:97:1f:c3:c2:dd:40:8e:\n                    5f:4a:28:04:36:fa:cf:7a:06:0c:b2:b0:ed:38:19:\n                    4e:72:78:02:59:1c:5b:1f:ee:a8:89:85:31:31:66:\n                    6e:cd:78:5b:b5:90:4d:b5:69:1a:fc:87:a3:03:ed:\n                    de:83:24:49:d5:be:fa:ec:21:55:52:ff:ee:13:5d:\n                    5f:0c:a6:7b:ff:52:2d:6d:8a:ca:32:47:31:8e:42:\n                    ba:ec:f7:f9:51:01:d2:62:47:e9:0b:9e:4e:1c:03:\n                    94:29:7a:83:d5:79:04:91:38:21:2e:2f:f7:bd:e6:\n                    3e:c7:dc:dd:e1:af:d8:72:ca:36:20:68:1a:a0:7a:\n                    77:b9\n                Exponent: 65537 (0x10001)\n        X509v3 extensions:\n            X509v3 Basic Constraints: \n                CA:FALSE\n            X509v3 Subject Key Identifier: \n                2B:DD:67:04:B9:F8:AF:FC:89:0C:F0:0D:4B:2A:6A:8B:6C:D6:CC:51\n            X509v3 Authority Key Identifier: \n                keyid:07:A4:13:ED:FA:8E:EB:7D:7A:BA:17:DE:FF:BE:7F:09:7C:E0:E5:C6\n                DirName:/CN=CloudMinds\n                serial:9D:5E:C8:7F:54:50:3B:85\n\n            X509v3 Extended Key Usage: \n                TLS Web Client Authentication\n            X509v3 Key Usage: \n                Digital Signature\n    Signature Algorithm: sha256WithRSAEncryption\n         6c:f9:2e:9e:7c:6f:45:36:2c:69:36:7b:ec:58:99:d3:df:c8:\n         84:30:2d:89:b2:f3:3b:42:20:8a:9b:af:b0:e1:d2:19:0b:8b:\n         c0:6d:73:38:27:56:67:82:1b:18:d2:f4:3d:7b:3d:36:53:e7:\n         79:00:87:a5:12:f8:0d:cb:b1:9c:60:16:ff:1b:08:be:ea:43:\n         1f:8e:b9:78:c3:c4:c6:bb:0a:8e:11:ce:9b:26:ba:44:52:9f:\n         79:61:76:56:1e:a3:93:1f:d8:99:16:7d:5a:54:ef:8f:1d:1b:\n         f1:22:c9:6e:3d:2c:a3:97:f4:d5:2d:3d:4f:60:cb:a0:09:02:\n         83:80:c6:26:75:90:b7:7f:30:19:b0:d0:fe:d5:e9:c3:9e:a5:\n         76:cc:5a:de:b3:aa:fc:d2:23:e7:4a:78:70:58:27:f0:7c:90:\n         5a:7a:55:e4:e4:ae:68:c9:c2:1d:dc:ad:f6:d7:c4:4b:be:26:\n         31:1e:db:7f:06:29:67:ce:52:58:7a:72:90:16:63:f7:5a:19:\n         f3:65:bc:ac:db:9c:55:47:50:57:61:12:71:4a:f3:88:20:5c:\n         ae:35:9f:41:10:6d:ed:6d:cf:db:30:53:35:59:6f:a2:ce:6a:\n         58:1e:73:11:f0:32:5f:ff:87:8d:3b:6a:73:cd:29:52:f8:5b:\n         ae:7c:d5:7c\n-----BEGIN CERTIFICATE-----\nMIIDOTCCAiGgAwIBAgIBNTANBgkqhkiG9w0BAQsFADAVMRMwEQYDVQQDEwpDbG91\nZE1pbmRzMB4XDTE2MTIxNDEwMDc1N1oXDTI2MTIxMjEwMDc1N1owEjEQMA4GA1UE\nAxMHamFuZS5saTCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAJmTBUyY\nKM00hJe+5hTRZkI8igQ3UUlS11r0qGOkys0Q/pbGvK18/Pzg+Lr40QUlklBZ1Dg5\nf/hTzxojOYskrX3lJGl94iR3y7eljBl0xVY+2mXSOMh9FSepq6GzbYo/i2qGdwA5\nDn4dSaVlK8h0uFXsmzc3meJFrOG5EKcU7yCXH8PC3UCOX0ooBDb6z3oGDLKw7TgZ\nTnJ4AlkcWx/uqImFMTFmbs14W7WQTbVpGvyHowPt3oMkSdW++uwhVVL/7hNdXwym\ne/9SLW2KyjJHMY5Cuuz3+VEB0mJH6QueThwDlCl6g9V5BJE4IS4v973mPsfc3eGv\n2HLKNiBoGqB6d7kCAwEAAaOBljCBkzAJBgNVHRMEAjAAMB0GA1UdDgQWBBQr3WcE\nufiv/IkM8A1LKmqLbNbMUTBFBgNVHSMEPjA8gBQHpBPt+o7rfXq6F97/vn8JfODl\nxqEZpBcwFTETMBEGA1UEAxMKQ2xvdWRNaW5kc4IJAJ1eyH9UUDuFMBMGA1UdJQQM\nMAoGCCsGAQUFBwMCMAsGA1UdDwQEAwIHgDANBgkqhkiG9w0BAQsFAAOCAQEAbPku\nnnxvRTYsaTZ77FiZ09/IhDAtibLzO0IgipuvsOHSGQuLwG1zOCdWZ4IbGNL0PXs9\nNlPneQCHpRL4DcuxnGAW/xsIvupDH465eMPExrsKjhHOmya6RFKfeWF2Vh6jkx/Y\nmRZ9WlTvjx0b8SLJbj0so5f01S09T2DLoAkCg4DGJnWQt38wGbDQ/tXpw56ldsxa\n3rOq/NIj50p4cFgn8HyQWnpV5OSuaMnCHdyt9tfES74mMR7bfwYpZ85SWHpykBZj\n91oZ82W8rNucVUdQV2EScUrziCBcrjWfQRBt7W3P2zBTNVlvos5qWB5zEfAyX/+H\njTtqc80pUvhbrnzVfA==\n-----END CERTIFICATE-----\n\r\n<\/cert>\r\n<key>\r\n-----BEGIN PRIVATE KEY-----\nMIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCZkwVMmCjNNISX\nvuYU0WZCPIoEN1FJUtda9KhjpMrNEP6WxrytfPz84Pi6+NEFJZJQWdQ4OX/4U88a\nIzmLJK195SRpfeIkd8u3pYwZdMVWPtpl0jjIfRUnqauhs22KP4tqhncAOQ5+HUml\nZSvIdLhV7Js3N5niRazhuRCnFO8glx/Dwt1Ajl9KKAQ2+s96BgyysO04GU5yeAJZ\nHFsf7qiJhTExZm7NeFu1kE21aRr8h6MD7d6DJEnVvvrsIVVS/+4TXV8Mpnv/Ui1t\nisoyRzGOQrrs9/lRAdJiR+kLnk4cA5QpeoPVeQSROCEuL/e95j7H3N3hr9hyyjYg\naBqgene5AgMBAAECggEBAIyn0xqr4fLQPwCcny5B+15/BAmIgLKfZQ6xNejNbdET\n15lQvE5JQ4x4oFbevNHx72gYXf6A62wNlolh6bo/BYdg71CPMygjcDnrLb3C7Vpo\nhq7rM4asyUtiYwfexlzwhkgyj5tsppFrUaBiYQ9fl9Vat26DRjWviv/R+CaPTdSS\nKgTqEAVZZupC6q0uZkMlMgL0JKnX3dT4i4SbrQqzuAzvJgxXJ9skUfD9MYzQ0bcf\nMlFWGIj9/17yhNASK6cFI2bPXYElg5khzd2z09RYX4ZxM4NzO5QtqlOREBe9eia3\nl+7AsZUYC1ch4+HUmqWfqVHM+ytqyptHZ8uj119opAECgYEAymi4FUV3RE9VkShq\njPc5g5K/Gi8x8tqIFzBFWi7oHEMhU9ivQ/H5mUWaiWhAuvvxjGonpkvCf/anjdPZ\nOlFcUnEsSknTpiIM/4b7GmOFpYdi147tx04kA6xNrILnlnm/ed9C7SkZu1MusWdZ\nAsSbVy13+055cq4rDivd5ZtxPvECgYEAwjxIahYBLq9xoX5NB4LrsvRzLHiXnxiB\nz/G0tNPQ3a6G29SU9iq0CGTosCmG0xaW72UXfBWTXOf9GpUYhtqI2Qg7XelU8plQ\nnRlKPyTJuB1SfOLWx/g/+eavmeHPg6bUo7JqfmO5dmuZNEGQ7JrfowzoMMaSQZNG\nq62oY+Lh1UkCgYEAkFiH1tuYR4XQPtm0ytzQDk3LrQ+1Ljy8StUo0DydWPJixRnF\nO85Hsw+ZOp63cuGTjBB0QDrM9VbgfNt0kgtNrJl6/WuS3WpxHJWVY2dP+xEG4zTR\ny42lT11Ec19TatNeQrp+TwU9jYKvzOIn7zpokY4if3yiTa95kvyjnUfyRfECgYBM\n9bO/jO51AADm+vMsabxYELFdyn5RBFXS/bMa/t5AJ7m5enZ3Px5MQSdNPXb1RnsR\naZ2vhaD469aGZNLuA8K7M57KK/yDjym4xV98u2fNspiPcRWm7/7xVmz3bNhbEowo\nPDYj1AGVITb31I7GoOrBYQjS4qrqnp8ewNT6ltfiOQKBgClzvzb2pKJ/T9lDurfu\neaFuSAWd3YnuwuVwPkFqGFkzse2C4b2PjsvAPsGa1wHgjEDPsikgJl+UEL0rxbjq\nw1y0k3uRdVMk6U819+2UCccheD9nygWCWij0SE5/kY43TpaKBCiCBcP1xTiLMI7C\n4q7wjDAbHdHN4cMgWZ1sO8iz\n-----END PRIVATE KEY-----\n\r\n<\/key>\r\n<tls-auth>\r\n#\r\n# 2048 bit OpenVPN static key\r\n#\r\n-----BEGIN OpenVPN Static key V1-----\r\nacc406f02099f6478f1d1d25deba632f\r\nb0b057e79dd20f0b553a198e87e21c46\r\nffeed09132179870c6a1602b96e2effb\r\n69570191bc068acb131be9a59461103f\r\n2bb646193cb38d3238069fd51582606a\r\ne8b2325fa327a3897de2b1c2d9014599\r\nede1608108a5510c7018f1c6bfd2b64a\r\nfff33a1a4e531140a377b9147cca703b\r\n1e5c8e6c302b7c105e329dd9d5169c18\r\nb995d92203e25565d5ffc2be583ebdac\r\n3ed1ae7b5fa5d795a105c3c6df094ebf\r\n004d81d03a479ccc02fee40534b10c31\r\nf51ba7a43e335abd381f6356a79760ed\r\ne0c57d27962a089ce931a9de56c399ac\r\n120a25bc32c98cd2842464e5ed8c8d6d\r\nf9010bf1673db26382474149065f2dc0\r\n-----END OpenVPN Static key V1-----\r\n<\/tls-auth>\r\n","hariAddress":{"port":8033,"ip":"10.11.35.245"},"pushAddress":{"privatePort":"1883","privateIp":"10.11.35.179","publicPort":"1883","publicIp":"10.11.35.179"},"initAppConfig":"{\"btprint\":{\"btPrintEnable\":true,\"mac\":\"00:13:04:07:13:82\",\"name\":\"btprint\"},\"charge\":{\"chargeEnable\":true},\"log\":{\"logEnable\":true},\"logo\":{\"url\":\"\",\"version\":0},\"mic\":{\"micEnable\":true,\"micHand\":\"right\"},\"readppt\":{\"readMsg\":\"78:18:41:E9:B0:A1&yang-PC\",\"readPptEnable\":true,\"readType\":\"bluetooth\"},\"tts\":{\"speakSpeed\":2,\"speakTune\":\"\",\"speaker\":\"Tony\",\"type\":\"EN\"},\"uwb\":{\"checkDistance\":\"1.5\",\"uwbAddress\":\"192.168.1.11\",\"uwbEnable\":true,\"uwbPort\":\"9669\"}}"}
     * errors : null
     */

    private String sys;
    private int code;
    private String messages;
    private DataBean data;
    private Object errors;

    public String getSys() {
        return sys;
    }

    public void setSys(String sys) {
        this.sys = sys;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public Object getErrors() {
        return errors;
    }

    public void setErrors(Object errors) {
        this.errors = errors;
    }

    public static class DataBean {
        /**
         * tenantCode : 10086
         * userCode : arvin
         * usePwd:
         * serviceCode : bank
         * vpnProfile : client
         * dev tun
         * proto tcp
         * sndbuf 0
         * rcvbuf 0
         * remote vpn.cloudminds-inc.com 443
         * resolv-retry infinite
         * nobind
         * persist-key
         * persist-tun
         * remote-cert-tls server
         * cipher AES-128-CBC
         * comp-lzo
         * setenv opt block-outside-dns
         * key-direction 1
         * verb 3
         * <ca>
         * -----BEGIN CERTIFICATE-----
         * MIIDMjCCAhqgAwIBAgIJAJ1eyH9UUDuFMA0GCSqGSIb3DQEBCwUAMBUxEzARBgNV
         * BAMTCkNsb3VkTWluZHMwHhcNMTYxMTI0MDYxMzM3WhcNMjYxMTIyMDYxMzM3WjAV
         * MRMwEQYDVQQDEwpDbG91ZE1pbmRzMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIB
         * CgKCAQEAuRrnVKT7A51yynz3alYUBu8upfcNmGMI6AlOodC3BQVjhG2ZNWEqfhoL
         * +pc+KB6xmsh7BJBtE0UC1mDZS6maw5TEuzibKN1eAkIGL0sDVp/iApWVJxa6jYIh
         * IgyrLMdinw+BdAli+9RzBBV5J9ypbKgDUTcgxvg9d2IRBJaCIv3KcUZ5DywVKXVl
         * TD8VO63WkdwT/LUl9UF4BbZ2h6TkOnALF77iZdww0n66U5aZ1szMAtvigeWnNm9Y
         * wqHZCFV8ZDOmYD+2v6E1KAyZ2AqYaMPm37razLnsVx0jaS3J/FoJ/Ltc0Z6HgYDP
         * lAeyxPoMNaRD5eLm/P1+VxULnG5AewIDAQABo4GEMIGBMB0GA1UdDgQWBBQHpBPt
         * +o7rfXq6F97/vn8JfODlxjBFBgNVHSMEPjA8gBQHpBPt+o7rfXq6F97/vn8JfODl
         * xqEZpBcwFTETMBEGA1UEAxMKQ2xvdWRNaW5kc4IJAJ1eyH9UUDuFMAwGA1UdEwQF
         * MAMBAf8wCwYDVR0PBAQDAgEGMA0GCSqGSIb3DQEBCwUAA4IBAQA9Gk+IDl7iHGZ5
         * XyABkzxCEfgnFG3U+9ZPYwpKBpZUB8Q3Zr8AWsEGSK5xMiBF60V6p9pB1gL6cg1q
         * YCY/+KSHQnzfmXowjV+J9pRfc+aE8XwV4yAMPEiWW7U1cdTG2LErA0tngITB0IZS
         * 7XTi1fvLobA+MUkOsO7+XEu/AuT695k6KbV5oWmE0winkA3lYUoToLd4+yCGXkYS
         * hSX+kGou0d2tBEOUB4wqGP0XUA0DH1VURDEGQIMZhpdyOyoD1ggEp7i2HBxWwAsu
         * 5Puwfl4XMjDji0zYLkTMlWKohfEslAbOPXtR+CG0/ys3rdCBiUgwqIX/qLorcYHx
         * vjfsSUwq
         * -----END CERTIFICATE-----
         *
         * </ca>
         * <cert>
         * Certificate:
         * Data:
         * Version: 3 (0x2)
         * Serial Number: 53 (0x35)
         * Signature Algorithm: sha256WithRSAEncryption
         * Issuer: CN=CloudMinds
         * Validity
         * Not Before: Dec 14 10:07:57 2016 GMT
         * Not After : Dec 12 10:07:57 2026 GMT
         * Subject: CN=jane.li
         * Subject Public Key Info:
         * Public Key Algorithm: rsaEncryption
         * Public-Key: (2048 bit)
         * Modulus:
         * 00:99:93:05:4c:98:28:cd:34:84:97:be:e6:14:d1:
         * 66:42:3c:8a:04:37:51:49:52:d7:5a:f4:a8:63:a4:
         * ca:cd:10:fe:96:c6:bc:ad:7c:fc:fc:e0:f8:ba:f8:
         * d1:05:25:92:50:59:d4:38:39:7f:f8:53:cf:1a:23:
         * 39:8b:24:ad:7d:e5:24:69:7d:e2:24:77:cb:b7:a5:
         * 8c:19:74:c5:56:3e:da:65:d2:38:c8:7d:15:27:a9:
         * ab:a1:b3:6d:8a:3f:8b:6a:86:77:00:39:0e:7e:1d:
         * 49:a5:65:2b:c8:74:b8:55:ec:9b:37:37:99:e2:45:
         * ac:e1:b9:10:a7:14:ef:20:97:1f:c3:c2:dd:40:8e:
         * 5f:4a:28:04:36:fa:cf:7a:06:0c:b2:b0:ed:38:19:
         * 4e:72:78:02:59:1c:5b:1f:ee:a8:89:85:31:31:66:
         * 6e:cd:78:5b:b5:90:4d:b5:69:1a:fc:87:a3:03:ed:
         * de:83:24:49:d5:be:fa:ec:21:55:52:ff:ee:13:5d:
         * 5f:0c:a6:7b:ff:52:2d:6d:8a:ca:32:47:31:8e:42:
         * ba:ec:f7:f9:51:01:d2:62:47:e9:0b:9e:4e:1c:03:
         * 94:29:7a:83:d5:79:04:91:38:21:2e:2f:f7:bd:e6:
         * 3e:c7:dc:dd:e1:af:d8:72:ca:36:20:68:1a:a0:7a:
         * 77:b9
         * Exponent: 65537 (0x10001)
         * X509v3 extensions:
         * X509v3 Basic Constraints:
         * CA:FALSE
         * X509v3 Subject Key Identifier:
         * 2B:DD:67:04:B9:F8:AF:FC:89:0C:F0:0D:4B:2A:6A:8B:6C:D6:CC:51
         * X509v3 Authority Key Identifier:
         * keyid:07:A4:13:ED:FA:8E:EB:7D:7A:BA:17:DE:FF:BE:7F:09:7C:E0:E5:C6
         * DirName:/CN=CloudMinds
         * serial:9D:5E:C8:7F:54:50:3B:85
         * <p>
         * X509v3 Extended Key Usage:
         * TLS Web Client Authentication
         * X509v3 Key Usage:
         * Digital Signature
         * Signature Algorithm: sha256WithRSAEncryption
         * 6c:f9:2e:9e:7c:6f:45:36:2c:69:36:7b:ec:58:99:d3:df:c8:
         * 84:30:2d:89:b2:f3:3b:42:20:8a:9b:af:b0:e1:d2:19:0b:8b:
         * c0:6d:73:38:27:56:67:82:1b:18:d2:f4:3d:7b:3d:36:53:e7:
         * 79:00:87:a5:12:f8:0d:cb:b1:9c:60:16:ff:1b:08:be:ea:43:
         * 1f:8e:b9:78:c3:c4:c6:bb:0a:8e:11:ce:9b:26:ba:44:52:9f:
         * 79:61:76:56:1e:a3:93:1f:d8:99:16:7d:5a:54:ef:8f:1d:1b:
         * f1:22:c9:6e:3d:2c:a3:97:f4:d5:2d:3d:4f:60:cb:a0:09:02:
         * 83:80:c6:26:75:90:b7:7f:30:19:b0:d0:fe:d5:e9:c3:9e:a5:
         * 76:cc:5a:de:b3:aa:fc:d2:23:e7:4a:78:70:58:27:f0:7c:90:
         * 5a:7a:55:e4:e4:ae:68:c9:c2:1d:dc:ad:f6:d7:c4:4b:be:26:
         * 31:1e:db:7f:06:29:67:ce:52:58:7a:72:90:16:63:f7:5a:19:
         * f3:65:bc:ac:db:9c:55:47:50:57:61:12:71:4a:f3:88:20:5c:
         * ae:35:9f:41:10:6d:ed:6d:cf:db:30:53:35:59:6f:a2:ce:6a:
         * 58:1e:73:11:f0:32:5f:ff:87:8d:3b:6a:73:cd:29:52:f8:5b:
         * ae:7c:d5:7c
         * -----BEGIN CERTIFICATE-----
         * MIIDOTCCAiGgAwIBAgIBNTANBgkqhkiG9w0BAQsFADAVMRMwEQYDVQQDEwpDbG91
         * ZE1pbmRzMB4XDTE2MTIxNDEwMDc1N1oXDTI2MTIxMjEwMDc1N1owEjEQMA4GA1UE
         * AxMHamFuZS5saTCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAJmTBUyY
         * KM00hJe+5hTRZkI8igQ3UUlS11r0qGOkys0Q/pbGvK18/Pzg+Lr40QUlklBZ1Dg5
         * f/hTzxojOYskrX3lJGl94iR3y7eljBl0xVY+2mXSOMh9FSepq6GzbYo/i2qGdwA5
         * Dn4dSaVlK8h0uFXsmzc3meJFrOG5EKcU7yCXH8PC3UCOX0ooBDb6z3oGDLKw7TgZ
         * TnJ4AlkcWx/uqImFMTFmbs14W7WQTbVpGvyHowPt3oMkSdW++uwhVVL/7hNdXwym
         * e/9SLW2KyjJHMY5Cuuz3+VEB0mJH6QueThwDlCl6g9V5BJE4IS4v973mPsfc3eGv
         * 2HLKNiBoGqB6d7kCAwEAAaOBljCBkzAJBgNVHRMEAjAAMB0GA1UdDgQWBBQr3WcEReportAlarmBean
         * ufiv/IkM8A1LKmqLbNbMUTBFBgNVHSMEPjA8gBQHpBPt+o7rfXq6F97/vn8JfODl
         * xqEZpBcwFTETMBEGA1UEAxMKQ2xvdWRNaW5kc4IJAJ1eyH9UUDuFMBMGA1UdJQQM
         * MAoGCCsGAQUFBwMCMAsGA1UdDwQEAwIHgDANBgkqhkiG9w0BAQsFAAOCAQEAbPku
         * nnxvRTYsaTZ77FiZ09/IhDAtibLzO0IgipuvsOHSGQuLwG1zOCdWZ4IbGNL0PXs9
         * NlPneQCHpRL4DcuxnGAW/xsIvupDH465eMPExrsKjhHOmya6RFKfeWF2Vh6jkx/Y
         * mRZ9WlTvjx0b8SLJbj0so5f01S09T2DLoAkCg4DGJnWQt38wGbDQ/tXpw56ldsxa
         * 3rOq/NIj50p4cFgn8HyQWnpV5OSuaMnCHdyt9tfES74mMR7bfwYpZ85SWHpykBZj
         * 91oZ82W8rNucVUdQV2EScUrziCBcrjWfQRBt7W3P2zBTNVlvos5qWB5zEfAyX/+H
         * jTtqc80pUvhbrnzVfA==
         * -----END CERTIFICATE-----
         *
         * </cert>
         * <key>
         * -----BEGIN PRIVATE KEY-----
         * MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCZkwVMmCjNNISX
         * vuYU0WZCPIoEN1FJUtda9KhjpMrNEP6WxrytfPz84Pi6+NEFJZJQWdQ4OX/4U88a
         * IzmLJK195SRpfeIkd8u3pYwZdMVWPtpl0jjIfRUnqauhs22KP4tqhncAOQ5+HUml
         * ZSvIdLhV7Js3N5niRazhuRCnFO8glx/Dwt1Ajl9KKAQ2+s96BgyysO04GU5yeAJZ
         * HFsf7qiJhTExZm7NeFu1kE21aRr8h6MD7d6DJEnVvvrsIVVS/+4TXV8Mpnv/Ui1t
         * isoyRzGOQrrs9/lRAdJiR+kLnk4cA5QpeoPVeQSROCEuL/e95j7H3N3hr9hyyjYg
         * aBqgene5AgMBAAECggEBAIyn0xqr4fLQPwCcny5B+15/BAmIgLKfZQ6xNejNbdET
         * 15lQvE5JQ4x4oFbevNHx72gYXf6A62wNlolh6bo/BYdg71CPMygjcDnrLb3C7Vpo
         * hq7rM4asyUtiYwfexlzwhkgyj5tsppFrUaBiYQ9fl9Vat26DRjWviv/R+CaPTdSS
         * KgTqEAVZZupC6q0uZkMlMgL0JKnX3dT4i4SbrQqzuAzvJgxXJ9skUfD9MYzQ0bcf
         * MlFWGIj9/17yhNASK6cFI2bPXYElg5khzd2z09RYX4ZxM4NzO5QtqlOREBe9eia3
         * l+7AsZUYC1ch4+HUmqWfqVHM+ytqyptHZ8uj119opAECgYEAymi4FUV3RE9VkShq
         * jPc5g5K/Gi8x8tqIFzBFWi7oHEMhU9ivQ/H5mUWaiWhAuvvxjGonpkvCf/anjdPZ
         * OlFcUnEsSknTpiIM/4b7GmOFpYdi147tx04kA6xNrILnlnm/ed9C7SkZu1MusWdZ
         * AsSbVy13+055cq4rDivd5ZtxPvECgYEAwjxIahYBLq9xoX5NB4LrsvRzLHiXnxiB
         * z/G0tNPQ3a6G29SU9iq0CGTosCmG0xaW72UXfBWTXOf9GpUYhtqI2Qg7XelU8plQ
         * nRlKPyTJuB1SfOLWx/g/+eavmeHPg6bUo7JqfmO5dmuZNEGQ7JrfowzoMMaSQZNG
         * q62oY+Lh1UkCgYEAkFiH1tuYR4XQPtm0ytzQDk3LrQ+1Ljy8StUo0DydWPJixRnF
         * O85Hsw+ZOp63cuGTjBB0QDrM9VbgfNt0kgtNrJl6/WuS3WpxHJWVY2dP+xEG4zTR
         * y42lT11Ec19TatNeQrp+TwU9jYKvzOIn7zpokY4if3yiTa95kvyjnUfyRfECgYBM
         * 9bO/jO51AADm+vMsabxYELFdyn5RBFXS/bMa/t5AJ7m5enZ3Px5MQSdNPXb1RnsR
         * aZ2vhaD469aGZNLuA8K7M57KK/yDjym4xV98u2fNspiPcRWm7/7xVmz3bNhbEowo
         * PDYj1AGVITb31I7GoOrBYQjS4qrqnp8ewNT6ltfiOQKBgClzvzb2pKJ/T9lDurfu
         * eaFuSAWd3YnuwuVwPkFqGFkzse2C4b2PjsvAPsGa1wHgjEDPsikgJl+UEL0rxbjq
         * w1y0k3uRdVMk6U819+2UCccheD9nygWCWij0SE5/kY43TpaKBCiCBcP1xTiLMI7C
         * 4q7wjDAbHdHN4cMgWZ1sO8iz
         * -----END PRIVATE KEY-----
         *
         * </key>
         * <tls-auth>
         * #
         * # 2048 bit OpenVPN static key
         * #
         * -----BEGIN OpenVPN Static key V1-----
         * acc406f02099f6478f1d1d25deba632f
         * b0b057e79dd20f0b553a198e87e21c46
         * ffeed09132179870c6a1602b96e2effb
         * 69570191bc068acb131be9a59461103f
         * 2bb646193cb38d3238069fd51582606a
         * e8b2325fa327a3897de2b1c2d9014599
         * ede1608108a5510c7018f1c6bfd2b64a
         * fff33a1a4e531140a377b9147cca703b
         * 1e5c8e6c302b7c105e329dd9d5169c18
         * b995d92203e25565d5ffc2be583ebdac
         * 3ed1ae7b5fa5d795a105c3c6df094ebf
         * 004d81d03a479ccc02fee40534b10c31
         * f51ba7a43e335abd381f6356a79760ed
         * e0c57d27962a089ce931a9de56c399ac
         * 120a25bc32c98cd2842464e5ed8c8d6d
         * f9010bf1673db26382474149065f2dc0
         * -----END OpenVPN Static key V1-----
         * </tls-auth>
         * <p>
         * hariAddress : {"port":8033,"ip":"10.11.35.245"}
         * pushAddress : {"privatePort":"1883","privateIp":"10.11.35.179","publicPort":"1883","publicIp":"10.11.35.179"}
         * initAppConfig : {"btprint":{"btPrintEnable":true,"mac":"00:13:04:07:13:82","name":"btprint"},"charge":{"chargeEnable":true},"log":{"logEnable":true},"logo":{"url":"","version":0},"mic":{"micEnable":true,"micHand":"right"},"readppt":{"readMsg":"78:18:41:E9:B0:A1&yang-PC","readPptEnable":true,"readType":"bluetooth"},"tts":{"speakSpeed":2,"speakTune":"","speaker":"Tony","type":"EN"},"uwb":{"checkDistance":"1.5","uwbAddress":"192.168.1.11","uwbEnable":true,"uwbPort":"9669"}}
         */

        private String tenantCode;
        private String userCode;
        private String userPwd;
        private String serviceCode;
        private String vpnProfile;
        private HariAddressBean hariAddress;
        private PushAddressBean pushAddress;
        private String initAppConfig;
        private String pushToken;
        private String robotType;

        private String fileUploadAddress;

        public void setFileUploadAddress(String robotType) {
            this.fileUploadAddress = fileUploadAddress;
        }

        public String getFileUploadAddress() {
            return fileUploadAddress;
        }

        public String getTenantCode() {
            return tenantCode;
        }

        public void setTenantCode(String tenantCode) {
            this.tenantCode = tenantCode;
        }

        public String getUserCode() {
            return userCode;
        }

        public void setUserCode(String userCode) {
            this.userCode = userCode;
        }

        public String getUserPwd() {
            return userPwd;
        }

        public void setUserPwd(String userPwd) {
            this.userPwd = userPwd;
        }

        public String getServiceCode() {
            return serviceCode;
        }

        public void setServiceCode(String serviceCode) {
            this.serviceCode = serviceCode;
        }

        public void setRobotType(String robotType) {
            this.robotType = robotType;
        }

        public String getRobotType() {
            return robotType;
        }

        public String getVpnProfile() {
            return vpnProfile;
        }

        public void setVpnProfile(String vpnProfile) {
            this.vpnProfile = vpnProfile;
        }

        public HariAddressBean getHariAddress() {
            return hariAddress;
        }

        public void setHariAddress(HariAddressBean hariAddress) {
            this.hariAddress = hariAddress;
        }

        public PushAddressBean getPushAddress() {
            return pushAddress;
        }

        public void setPushAddress(PushAddressBean pushAddress) {
            this.pushAddress = pushAddress;
        }

        public String getInitAppConfig() {
            return initAppConfig;
        }

        public void setInitAppConfig(String initAppConfig) {
            this.initAppConfig = initAppConfig;
        }

        public String getPushToken() {
            return pushToken;
        }

        public void setPushToken(String pushToken) {
            this.pushToken = pushToken;
        }

        public static class HariAddressBean {
            /**
             * port : 8033
             * ip : 10.11.35.245
             */

            private int port;
            private String ip;

            public int getPort() {
                return port;
            }

            public void setPort(int port) {
                this.port = port;
            }

            public String getIp() {
                return ip;
            }

            public void setIp(String ip) {
                this.ip = ip;
            }
        }

        public static class PushAddressBean {
            /**
             * privatePort : 1883
             * privateIp : 10.11.35.179
             * publicPort : 1883
             * publicIp : 10.11.35.179
             */

            private String privatePort;
            private String privateIp;
            private String publicPort;
            private String publicIp;

            public String getPrivatePort() {
                return privatePort;
            }

            public void setPrivatePort(String privatePort) {
                this.privatePort = privatePort;
            }

            public String getPrivateIp() {
                return privateIp;
            }

            public void setPrivateIp(String privateIp) {
                this.privateIp = privateIp;
            }

            public String getPublicPort() {
                return publicPort;
            }

            public void setPublicPort(String publicPort) {
                this.publicPort = publicPort;
            }

            public String getPublicIp() {
                return publicIp;
            }

            public void setPublicIp(String publicIp) {
                this.publicIp = publicIp;
            }
        }
    }
}
