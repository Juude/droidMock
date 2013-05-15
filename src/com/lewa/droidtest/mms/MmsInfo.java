
package com.lewa.droidtest.mms;

import android.content.Context;
import android.net.Uri;

import com.google.android.mms.pdu.CharacterSets;
import com.google.android.mms.pdu.EncodedStringValue;
import com.google.android.mms.pdu.PduBody;
import com.google.android.mms.pdu.PduComposer;
import com.google.android.mms.pdu.PduPart;
import com.google.android.mms.pdu.SendReq;
import com.google.android.mms.ContentType;

import java.io.File;

public class MmsInfo {
    private Context mContext;
    private PduBody mPduBody;
    private String mDestination;
    private String mTitle;

    public MmsInfo(Context con, String number, String title) {
        mContext = con;
        mDestination = number;
        mPduBody = new PduBody();
        mTitle = title;
    }

    public void addImagePart(File f) {
        PduPart part = new PduPart();
        part.setCharset(CharacterSets.UTF_8);
        part.setName("CAMERA".getBytes());
        part.setContentType(ContentType.IMAGE_JPEG.getBytes());
        part.setDataUri(Uri.fromFile(f));
        mPduBody.addPart(part);
    }

    public byte[] getMMSBytes() {
        PduComposer composer = new PduComposer(mContext, initSendReq());
        return composer.make();
    }

    private SendReq initSendReq() {
        SendReq req = new SendReq();
        EncodedStringValue[] sub = EncodedStringValue.extract(mTitle);
        if (sub != null && sub.length > 0) {
            req.setSubject(sub[0]);
        }
        EncodedStringValue[] rec = EncodedStringValue.extract(mDestination);
        if (rec != null && rec.length > 0) {
            req.addTo(rec[0]);
        }
        req.setBody(mPduBody);
        return req;
    }

}
